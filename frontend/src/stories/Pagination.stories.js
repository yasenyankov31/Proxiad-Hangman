// PaginationComponent.stories.js

import React from 'react';
import { action } from '@storybook/addon-actions';
import PaginationComponent from '../components/general/Pagination';

export default {
  title: 'general/PaginationComponent',
  component: PaginationComponent,
};

const Template = (args) => <PaginationComponent {...args} />;

export const Default = Template.bind({});
Default.args = {
  totalPages: 5,
  totalElements: 50,
  pageNumber: 1,
  handlePagination: action('handlePagination'),
};
