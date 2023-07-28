// Home.stories.js
import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import Home from '../components/statistics/Home';
import fetchMock from 'fetch-mock';



fetchMock.get('/api/games',{
    month: {
      "userNames": [
        "qska",
        "zapo",
        "pro"
      ],
      "winCounts": [
        4,6,4
      ]
    },
    allTime: {
      "userNames": [
        "ivan"
      ],
      "winCounts": [
        5
      ]
    }
  });


// Mock navigate function
const mockNavigate = () => {
  console.log('Navigate function called');
};


// Mock wrapper component
const WrapperComponent = () => {
    return (
        <MemoryRouter initialEntries={['/']} initialIndex={0}>
            <Home navigate={mockNavigate} />
        </MemoryRouter>
    );
};

export default {
    title: 'statistics/Home',
    component: Home,
};

const Template = (args) => <WrapperComponent {...args} />;

export const Default = Template.bind({});
Default.args = {};
