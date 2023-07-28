// ParentNavbar.stories.js

import React from 'react';
import { action } from '@storybook/addon-actions';
import { MemoryRouter } from 'react-router-dom';
import ParentNavbar from '../components/general/Navbar';

export default {
    title: 'general/ParentNavbar',
    component: ParentNavbar,
    decorators: [(getStory) => <MemoryRouter initialEntries={['/']}>{getStory()}</MemoryRouter>],
};

const Template = (args) => <ParentNavbar {...args} />;

export const Default = Template.bind({});
