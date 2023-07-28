// Loading.stories.js

import React from 'react';
import Loading from '../components/general/Loading';

export default {
    title: 'general/Loading',
    component: Loading,
};

const Template = (args) => <Loading {...args} />;

export const Default = Template.bind({});
