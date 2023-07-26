import React, { useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { Container } from 'react-bootstrap';

function UserModal({ show, closeModal, userData, updateUsers, isDeleting, deleteIds }) {
  const [userId, setUserId] = useState(0);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [age, setAge] = useState(0);
  const [birthDate, setBirthDate] = useState("");

  const [action, setAction] = useState("Add");
  const [deleteParams, setDeleteParams] = useState("");
  const [deleteMessage, setDeleteMessage] = useState("Are you sure you want to delete?");

  const [executionStatus, setExecutionStatus] = useState("");

  const person = {
    "Add": "POST",
    "Edit": "PUT",
    "Delete": "DELETE"
  };



  const submitModal = () => {
    fetch('/api/users' + deleteParams, {
      method: person[action],
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        id: userId,
        username: username,
        password: password,
        age: age,
        birthDate: birthDate
      })

    })
      .then(response => response.json())
      .then(successMessage => {
        setAction("Processed");
        setExecutionStatus(successMessage.message);
        updateUsers();
      })
      .catch((errorMessage) => {
        setAction("Processed");
        setExecutionStatus(errorMessage.message);
      });
    
      setDeleteParams("");

  }
  useEffect(()=>{
    setDeleteParams("");
  },[deleteIds])


  useEffect(() => {
    if (isDeleting) {
      setAction("Delete");
      if (deleteIds.size !== 0) {
        let params = "?";
        for (let value of deleteIds) {
          params += deleteParams + "ids=" + value + "&";
        }
        setDeleteParams(params.slice(0, -1));

        setDeleteMessage("Are you sure you want to delete?");
      }
      else {
        setDeleteMessage("No users selected!");
      }
    }
    else if (userData === null) {
      setAction("Add");
      setUsername("");
      setPassword("");
      setAge(0);
      setBirthDate('');

    }
    else {
      setAction("Edit");
      setUserId(userData.id);
      setUsername(userData.username);
      setPassword(userData.password);
      setAge(userData.age);
      setBirthDate(userData.birthDate);
    }
  }, [show])


  const renderModalBody = () => {
    switch (action) {
      case "Add":
      case "Edit":
        return (
          <>
            <Modal.Body>
              <Form>
                <Form.Group className="mb-3">
                  <Form.Label>Username</Form.Label>
                  <Form.Control defaultValue={username} onChange={(e) => { setUsername(e.target.value) }} type="text" placeholder="ProPlayer123" />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Password</Form.Label>
                  <Form.Control defaultValue={password} onChange={(e) => { setPassword(e.target.value) }} type="text" placeholder="Original password" />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Age</Form.Label>
                  <Form.Control value={age} onChange={(e) => { setAge(Number(e.target.value)) }} type="number" placeholder="Your age" />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Birth date</Form.Label>
                  <Form.Control defaultValue={birthDate} onChange={(e) => { setBirthDate(e.target.value) }} type="date" />
                </Form.Group>
              </Form>
            </Modal.Body>
            <Modal.Footer className="justify-content-start">
              <Button variant="primary" onClick={submitModal}>
                {action}
              </Button>
            </Modal.Footer>
          </>
        );
      case "Delete":
        return (
          <div className='d-flex justify-content-center align-items-center' style={{ marginTop: "50px", marginBottom: "50px" }} >
            <Container className='text-center'>
              <h5 >{deleteMessage}</h5>
              {deleteIds.size>0 && <Button className='btn-danger' onClick={submitModal}>Delete</Button>}
            </Container>
          </div>
        );
      case "Processed":
        return (
          <div className='d-flex justify-content-center align-items-center' style={{ marginTop: "50px", marginBottom: "50px" }} >
            <h5 className='text-center text-success'>{executionStatus}</h5>
          </div>
        );
      default:
        return null;
    }
  }


  return (
    <>
      <Modal show={show} onHide={closeModal}>
        <Modal.Header closeButton>
          <Modal.Title>{action} user</Modal.Title>
        </Modal.Header>

        {renderModalBody()}
      </Modal>
    </>
  );
}

export default UserModal;