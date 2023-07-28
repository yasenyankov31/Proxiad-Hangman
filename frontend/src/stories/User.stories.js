import React from 'react';
import User from '../components/users/User';
import { Routes, Route, MemoryRouter } from 'react-router-dom';
import fetchMock from 'fetch-mock';


fetchMock.get('/api/users/user-profile?username=qska',{
  winCount: 10,
  lossCount: 5,
  statusValues: [1, 2, 3, 4, 5],
});
fetchMock.get('/api/users/played-games?username=qska&pageNum=1',{
  totalPages: 1,
  totalElements: 1,
  content: [
    {
      word: 'example',
      attemptsLeft: 5,
      gameStatus: 'Active',
      startDate: '2023-07-30T00:00:00.000Z',
      lettersUsed: 'a, e',
    },
  ]
});

export default {
  title: 'user/User',
  component: User,
  decorators: [(Story) => (
    <MemoryRouter initialEntries={["/user/qska/1"]}>
      <Routes>
        <Route path="/user/:username/:page" element={<Story />} />
      </Routes>
    </MemoryRouter>)],
};

const Template = (args) => <User {...args} />;

export const Default = Template.bind({});
