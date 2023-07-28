import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import RankingBars from '../components/statistics/RankingBars';

export default {
  title: 'statistics/RankingBars',
  component: RankingBars,
  decorators: [(Story) => (
    <MemoryRouter>
      <Story />
    </MemoryRouter>
  )],
};

const Template = (args) => <RankingBars {...args} />;

export const Default = Template.bind({});
Default.args = {
  userNames: ['User 1', 'User 2', 'User 3'],
  winCounts: [5, 8, 2]
};
