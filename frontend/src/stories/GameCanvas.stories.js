// GameCanvas.stories.js

import React from 'react';
import { action } from '@storybook/addon-actions';
import GameCanvas from '../components/games/GameCanvas';

export default {
    title: 'game/GameCanvas',
    component: GameCanvas,
};

const Template = (args) => <GameCanvas {...args} />;

export const EightAttempts = Template.bind({});
EightAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 8,
    resetGame: action('Reset game'),
    gameStatus:"OnGoing"
};

export const SevenAttempts = Template.bind({});
SevenAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 7,
    resetGame: action('Reset game'),
};

export const SixAttempts = Template.bind({});
SixAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 6,
    resetGame: action('Reset game'),
};

export const FiveAttempts = Template.bind({});
FiveAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 5,
    resetGame: action('Reset game'),
};

export const FourAttempts = Template.bind({});
FourAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 4,
    resetGame: action('Reset game'),
};

export const ThreeAttempts = Template.bind({});
ThreeAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 3,
    resetGame: action('Reset game'),
};

export const TwoAttempts = Template.bind({});
TwoAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 2,
    resetGame: action('Reset game'),
};

export const OneAttempt = Template.bind({});
OneAttempt.args = {
    wordToGuess: 'h****o',
    attempts: 1,
    resetGame: action('Reset game'),
};

export const ZeroAttempts = Template.bind({});
ZeroAttempts.args = {
    wordToGuess: 'h****o',
    attempts: 0,
    resetGame: action('Reset game'),
};
