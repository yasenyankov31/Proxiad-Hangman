import { Container, Button, Table, Form } from 'react-bootstrap';
import Loading from '../general/Loading';
import ErrorComponent from '../general/Error';
import useSWR from 'swr'
import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import PaginationComponent from '../general/Pagination';
import UserModal from './UserModal';


const fetcher = async (url) => {
    const response = await fetch(url);
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
    return response.json();
};


const Users = () => {
    const navigate = useNavigate();
    const { page } = useParams();
    const { data, error, isLoading } = useSWR('/api/users?pageNum=' + page, fetcher);

    const [users, setUsers] = useState([]);
    const [pageNumber, setPageNumber] = useState(page);
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [showUserModal, setShowUserModal] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);
    const [selectedUserIds, setSelectedUserIds] = useState(new Set());
    const [selectedUserIdsModal, setSelectedUserIdsModal] = useState(new Set());

    const [allCheck, setAllCheck] = useState(false);
    const [isDeleting, setIsDeleting] = useState(false);

    const [errorFetching, setErrorFetching] = useState(null);



    function toggleUserSelection(userId, isChecked) {
        setSelectedUserIds((prevSelectedUserIds) => {
            const newSelectedUserIds = new Set(prevSelectedUserIds);
            if (isChecked) {
                newSelectedUserIds.add(userId);
            } else {
                newSelectedUserIds.delete(userId);
            }
            return newSelectedUserIds;
        });
    }

    function toggleSelectAll(isChecked) {
        setAllCheck(!allCheck);
        setSelectedUserIds((prevSelectedUserIds) => {
            if (isChecked) {
                return new Set(users.map(user => user.id));
            } else {
                return new Set();
            }
        });
    }

    const handlePagination = (pageNumber) => {
        setSelectedUserIds(new Set());
        setAllCheck(false);
        fetch("/api/users?pageNum=" + pageNumber, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(users => {
                setTotalElements(users.totalElements);
                setUsers(users.content);
                setTotalPages(users.totalPages);
            });
        setPageNumber(pageNumber);
    }

    const closeModal = () => {
        setAllCheck(false);
        setSelectedUserIds(new Set());
        setIsDeleting(false);
        setShowUserModal(false);
        setSelectedUser(null);
    }
    const showModal = () => {
        setShowUserModal(true);
    }

    const viewUserProfile = (username) => {
        navigate("/user/" + username + "/1");
    }

    const editUser = (user) => {
        setShowUserModal(true);
        setSelectedUser(user);
    }

    const deleteUser = (ids) => {
        setSelectedUserIdsModal(ids);
        setIsDeleting(true);
        setShowUserModal(true);

    }

    const updateUserData = () => {
        fetch('/api/users?pageNum=' + pageNumber, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(users => {
                setTotalElements(users.totalElements);
                setUsers(users.content);
                setTotalPages(users.totalPages);
            })
            .catch((error) => {
                setErrorFetching(error);
            });
    }



    useEffect(() => {
        if (data) {
            setTotalElements(data.totalElements);
            setUsers(data.content);
            setTotalPages(data.totalPages);
            setPageNumber(Number(page));
        }

    }, [data]);


    if (error) return <ErrorComponent message={"An error occured fetching the games ranking data."
        + error.message} />
    if (errorFetching) return <ErrorComponent message={"An error occured fetching the games ranking data."
        + errorFetching} />
    if (isLoading) return <Loading />



    return (
        <Container className="d-flex flex-column align-items-center justify-content-center ">
            <UserModal
                show={showUserModal}
                closeModal={closeModal}
                userData={selectedUser}
                updateUsers={updateUserData}
                deleteIds={selectedUserIdsModal}
                isDeleting={isDeleting}
            />
            <div className="p-5">
                <h1 className="p-5 d-flex flex-column align-items-center justify-content-center">All users</h1>
                <div className=" justify-content-start">
                    <Button className='m-1 btn-primary' onClick={() => { showModal() }}>Add new user</Button>
                    <Button className='m-1 btn-danger' onClick={() => { deleteUser(selectedUserIds) }}>Delete selected</Button>
                </div>
                <Table className='table-dark' striped bordered >
                    <thead>
                        <tr>
                            <th className='text-center'>Select all
                                <Form.Check
                                    type="checkbox"
                                    id="select-all"
                                    checked={allCheck}
                                    onChange={e => toggleSelectAll(e.target.checked)}
                                />
                            </th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Age</th>
                            <th>Birth Date</th>
                            <th>Actions</th>
                            <th>View profile</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map((user, index) => {
                            const date = new Date(user.birthDate);
                            return (
                                <tr key={index}>
                                    <td className="text-center">
                                        <Form.Check
                                            type="checkbox"
                                            id={`select-${index}`}
                                            onChange={e => toggleUserSelection(user.id, e.target.checked)}
                                            checked={selectedUserIds.has(user.id)}
                                        />
                                    </td>
                                    <td id="username">{user.username}</td>
                                    <td id="password">{user.password}</td>
                                    <td id="age">{user.age}</td>
                                    <td id="birthDate">{date.toLocaleDateString()}</td>
                                    <td className="text-center">
                                        <a className="text-warning p-2" onClick={() => { editUser(user) }} >
                                            <i className="material-icons" title="Edit">&#xE254;</i>
                                        </a>
                                        <a className="text-danger" onClick={() => {
                                            const singleIdSet = new Set([Number(user.id)]);
                                            deleteUser(singleIdSet);
                                        }}>
                                            <i className="material-icons" title="Delete">&#xE872;</i>
                                        </a>
                                    </td>
                                    <td className="text-center">
                                        <a className="view" onClick={() => { viewUserProfile(user.username) }}>
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-person-fill" viewBox="0 0 16 16">
                                                <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z" />
                                            </svg>
                                        </a>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </Table>

                <PaginationComponent
                    totalPages={totalPages}
                    pageNumber={pageNumber}
                    totalElements={totalElements}
                    handlePagination={handlePagination} />
            </div>
        </Container >

    );
};


export default Users;
