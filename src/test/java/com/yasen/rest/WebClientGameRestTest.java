package com.yasen.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.game_classes.models.dto.GameDto;

class WebClientGameRestTest {
	private static final String url = "http://localhost:8080/api/games";

	private static WebClient webClient;

	GameDto creategame() {
		webClient = WebClient.create(url);
		ResponseEntity<GameDto> new_game = webClient.post().uri("/game").retrieve().toEntity(GameDto.class).block();

		return new_game.getBody();
	}

	@Test
	void createGameTest() {
		webClient = WebClient.create(url);
		ResponseEntity<GameDto> new_game = webClient.post().uri("/game").retrieve().toEntity(GameDto.class).block();

		assertNotNull(new_game.getBody());
	}

	@Test
	void getGameByIdTest() {

		GameDto createdGame = creategame();
		webClient = WebClient.create(url);

		ResponseEntity<GameDto> object = webClient.get().uri("/game/{id}", createdGame.getId()).retrieve()
				.toEntity(GameDto.class).block();

		GameDto game = object.getBody();

		assertNotNull(game);

		assertEquals(game.getId(), createdGame.getId());

		assertEquals(game.getAttemptsLeft(), createdGame.getAttemptsLeft());

		assertEquals(game.getGuessedWord(), createdGame.getGuessedWord());

		assertEquals(game.getDate().toString(), createdGame.getDate().toString());
	}

	@Test
	void getNotCompleteGameTest() {
		// Create a RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Send a GET request and retrieve the response
		ResponseEntity<Object> response = restTemplate.getForEntity(url + "/not-completed-games/0", Object.class);

		assertEquals(200, response.getStatusCodeValue());

		assertEquals(true, response.hasBody());
	}

	@Test
	void resetGameByIdTest() {

		GameDto createdGame = creategame();
		webClient = WebClient.create(url);

		ResponseEntity<GameDto> object = webClient.put().uri("/game/reset/{id}", createdGame.getId()).retrieve()
				.toEntity(GameDto.class).block();

		GameDto game = object.getBody();

		assertNotNull(game);

		assertEquals(game.getAttemptsLeft(), createdGame.getAttemptsLeft());

		assertNotEquals(game.getGuessedWord(), createdGame.getGuessedWord());

		assertNotEquals(game.getGuessedWord(), "");

		assertEquals(game.getDate().toString(), createdGame.getDate().toString());
	}

	@Test
	void guessLetterTest() throws FileNotFoundException {

		GameDto createdGame = creategame();

		String wordToTest = "";

		File file = new File("wordlist.txt");
		int index = 0;

		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				if (index == createdGame.getWordNum()) {
					wordToTest = data;
				}

				index++;
			}
		}
		var missingLetters = getMissingLetters(wordToTest.toUpperCase());
		ResponseEntity<GameDto> object = webClient.put()
				.uri("/game/{id}/guess-letter?letter=" + missingLetters[0], createdGame.getId()).retrieve()
				.toEntity(GameDto.class).block();

		GameDto guessedGame = object.getBody();
		String flag = null;

		if (guessedGame.getAttemptsLeft() != createdGame.getAttemptsLeft()) {
			flag = "changed";
		}
		assertNotNull(flag);
	}

	private char[] getMissingLetters(String word) {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder missingLetters = new StringBuilder();

		for (char letter : alphabet.toCharArray()) {
			if (word.indexOf(letter) == -1) {
				missingLetters.append(letter);
			}
		}

		return missingLetters.toString().toCharArray();
	}
}
