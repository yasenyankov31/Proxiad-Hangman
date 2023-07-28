import React from 'react';
import { expect } from '@storybook/jest';
import UserModal from '../components/users/UserModal';
import fetchMock from 'fetch-mock';


fetchMock.delete("/api/users?ids=2&ids=3&ids=4", {
  message: "Delete done!"
});

export default {
  title: 'user/UserModal',
  component: UserModal,
  argTypes: {
    show: { control: 'boolean' },
    userData: { control: 'object' },
    isDeleting: { control: 'boolean' },
    deleteIds: { control: 'object' },
  },
};

const Template = (args) => <UserModal {...args} />;

export const Create = Template.bind({});
Create.args = {
  show: true,
  userData: {},
  isDeleting: false,
  deleteIds: new Set(),
  closeModal: () => { },
  updateUsers: () => { }
};

Create.play = async () => {
  const parent = document.querySelector(".modal-dialog");
  
  await new Promise(resolve => setTimeout(resolve, 2000));
  const button = parent.getElementsByClassName("btn")[0];
  button.click();
  
  await new Promise(resolve => setTimeout(resolve, 1000));
  const message = parent.getElementsByClassName("text-success")[0];
  expect(message.innerText).toBe("Submit done!");
};


export const Edit = Template.bind({});
Edit.args = {
  show: true,
  userData: { id: 1, username: 'testUser', password: 'testPassword', age: 25, birthDate: '1998-07-26' },
  isDeleting: false,
  deleteIds: new Set(),
  closeModal: () => { },
  updateUsers: () => { }
};

Edit.play = async () => {
  const parent = document.querySelector(".modal-dialog");
  
  await new Promise(resolve => setTimeout(resolve, 2000));
  const button = parent.getElementsByClassName("btn")[0];
  button.click();
  
  await new Promise(resolve => setTimeout(resolve, 1000));
  const message = parent.getElementsByClassName("text-success")[0];
  expect(message.innerText).toBe("Submit done!");
};

export const Delete = Template.bind({});
Delete.args = {
  show: true,
  userData: {},
  isDeleting: true,
  deleteIds: new Set([2, 3, 4]),
  closeModal: () => { },
  updateUsers: () => { }
};

Delete.play = async () => {
  const parent = document.querySelector(".modal-dialog");
  
  await new Promise(resolve => setTimeout(resolve, 2000));
  const button = parent.getElementsByClassName("btn")[0];
  button.click();
  
  await new Promise(resolve => setTimeout(resolve, 1000));
  const message = parent.getElementsByClassName("text-success")[0];
  expect(message.innerText).toBe("Delete done!");
};