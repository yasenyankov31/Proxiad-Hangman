// Error.stories.js

import React from 'react';
import Error from '../components/general/Error';

export default {
    title: 'general/Error',
    component: Error,
};

const Template = (args) => <Error {...args} />;

export const Default = Template.bind({});
Default.args = {
    message: 'An error occurred!',
};
