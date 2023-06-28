package com.game_classes;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.game_classes.models.Game;

class GameServiceTest {

	@Mock
	GameRepositoryImpl gameRepository;

	@InjectMocks
	private GameServiceImpl gameServiceImpl;

	private Game game;

	long gameId = 242983472832432432L;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		game = new Game(gameId, "example", "*******", 7, 2);
	}

	@Test
	void getRandomWordTest() {
		assertNotNull(gameServiceImpl.getRandomWord());
	}

	@Test
	void getCreateNewGameTest() {
		assertNotNull(gameServiceImpl.createNewGame());
	}

	@Test
  void guessLetterTest() {
    // Create a new game
    when(gameRepository.findById(game.getId())).thenReturn(game);

    // Call the method under test
    char letter = 'e';
    Game result = gameServiceImpl.guessLetter(gameId, letter);

    // Verify that gameRepository methods were called
    verify(gameRepository, times(1)).findById(gameId);
    verify(gameRepository, times(1)).updateGame(result);

    // Assert that the returned game is not null
    assertNotNull(result);
  }

	@Test
  void getGameStateTest() {
    // Create a new game
    when(gameRepository.findById(game.getId())).thenReturn(game);
    Game result = gameServiceImpl.getGameState(gameId);

    // Verify that gameRepository methods were called
    verify(gameRepository, times(1)).findById(gameId);

    // Assert that the returned game is not null
    assertNotNull(result);
  }

	@Test
	void getUsersLettersTest() {
		// Create a new game
		game.setLettersUsed("test");
		when(gameRepository.findById(game.getId())).thenReturn(game);
		String result = gameServiceImpl.getUsersLetters(gameId);

		// Verify that gameRepository methods were called
		verify(gameRepository, times(1)).findById(gameId);

		// Assert that the returned game is not null
		assertEquals("test", result);
	}

	@Test
  void resetGameTest() {
    // Create a new game
    when(gameRepository.findById(game.getId())).thenReturn(game);

    Game result = gameServiceImpl.resetGame(gameId);

    // Verify that gameRepository methods were called
    verify(gameRepository, times(1)).findById(gameId);
    verify(gameRepository, times(1)).updateGame(any(Game.class));

    // Assert that the returned game is not null
    assertEquals(7, result.getAttemptsLeft());
    assertEquals("sampleId", result.getId());
    assertEquals("", result.getLetters());
  }
}
