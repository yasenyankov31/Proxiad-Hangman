import React from 'react';
import Users from '../components/users/Users';
import { Routes, Route, MemoryRouter } from 'react-router-dom';
import fetchMock from 'fetch-mock';

fetchMock.post("/api/users", {
    message: "Submit done!"
});

fetchMock.put("/api/users", {
    message: "Submit done!"
});

fetchMock.delete("/api/users?ids=1&ids=2", {
    message: "Delete done!"
});

fetchMock.delete("/api/users?ids=1", {
    message: "Delete done!"
});

fetchMock.delete("/api/users?ids=2", {
    message: "Delete done!"
});



fetchMock.get('/api/users?pageNum=1', {
    totalPages: 1,
    totalElements: 1,
    content: [
        {
            id: 1,
            username: 'test1',
            password: 'testPassword',
            age: 15,
            birthDate: '2023-07-30T00:00:00.000Z'
        },
        {
            id: 2,
            username: 'test2',
            password: 'testPassword2',
            age: 30,
            birthDate: '2024-07-30T00:00:00.000Z'
        },
    ]
});

export default {
    title: 'user/Users',
    component: Users,
    decorators: [(Story) => (
        <MemoryRouter initialEntries={["/users/1"]}>
            <Routes>
                <Route path="/users/:page" element={<Story />} />
            </Routes>
        </MemoryRouter>)],
};

const Template = (args) => <Users {...args} />;

export const Default = Template.bind({});
