import React from 'react';
import Game from '../components/games/Game';
import { Routes, Route, MemoryRouter } from 'react-router-dom';
import fetchMock from 'fetch-mock';
import { expect } from '@storybook/jest';
import { within } from '@storybook/testing-library';

const alphabet = 'abcdefghijklmnopqrstuvwxyz';


fetchMock.post("/api/games", {
    id: 1,
    attemptsLeft: 8,
    completedGame: false,
    guessedWord: "h***o",
    lettersUsed: "",
    gameStatus: "OnGoing"
});



fetchMock.get('/api/games/1', {
    attemptsLeft: 8,
    completedGame: false,
    guessedWord: "h***o",
    lettersUsed: "",
    gameStatus: "OnGoing"
});

fetchMock.get("/api/games/1/ending-result?username=", {
    attemptsLeft: 8,
    originalWord:"test",
    completedGame: true,
    guessedWord: "h***o",
    lettersUsed: "",
    gameStatus: "Won"
});

fetchMock.put('/api/games/1/reset', {
    attemptsLeft: 8,
    completedGame: false,
    guessedWord: "h***o",
    lettersUsed: "",
    gameStatus: "OnGoing"
});

for (let letter of alphabet) {
    fetchMock.put(`/api/games/1/guess-letter?letter=${letter}`, {
        completedGame: false,
        guessedWord: "h***o",
        lettersUsed: "",
        gameStatus: "Won"
    });
}

export default {
    title: 'game/Game',
    component: Game,
    decorators: [(Story) => (
        <MemoryRouter initialEntries={["/game/1"]}>
            <Routes>
                <Route path="/game/:id" element={<Story />} />
            </Routes>
        </MemoryRouter>)],
};

const Template = (args) => <Game {...args} />;

export const Default = Template.bind({});

Default.play = async () => {
    // Initial delay
    await new Promise(r => setTimeout(r, 1000));

    // Click on the keyboard button
    const keyboard = document.querySelector(".keyboard");
    const letterA = keyboard.getElementsByTagName("button")[0];
    letterA.click();

    // Set value for the input field and dispatch change event
    await new Promise(r => setTimeout(r, 1000));
    const parent = document.querySelector(".parent");

    await new Promise(r => setTimeout(r, 3000));
    const submitButton = parent.getElementsByTagName("button")[0];
    submitButton.click();

    setTimeout(() => {
        const labelElement = document.querySelector('.status');
        expect(labelElement.innerHTML).toBe("You Won the game.The word was test!");
    }, 1000);


};