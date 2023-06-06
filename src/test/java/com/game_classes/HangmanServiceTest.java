package com.game_classes;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HangmanServiceTest {

  @Mock InMemoryGameRepository gameRepository;

  @InjectMocks private GameServiceImpl gameServiceImpl;

  Game game;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    game = new Game("sampleId", "example", "*******", 7);
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
    Game result = gameServiceImpl.guessLetter("sampleId", letter);

    // Verify that gameRepository methods were called
    verify(gameRepository, times(1)).findById(anyString());
    verify(gameRepository, times(1)).save(any(Game.class));

    // Assert that the returned game is not null
    assertNotNull(result);
  }

  @Test
  void getGameStateTest() {
    // Create a new game
    when(gameRepository.findById(game.getId())).thenReturn(game);
    Game result = gameServiceImpl.getGameState("sampleId");

    // Verify that gameRepository methods were called
    verify(gameRepository, times(1)).findById(anyString());

    // Assert that the returned game is not null
    assertNotNull(result);
  }

  @Test
  void getUsersLettersTest() {
    // Create a new game
    game.setLettersUsed("test");
    when(gameRepository.findById(game.getId())).thenReturn(game);
    String result = gameServiceImpl.getUsersLetters("sampleId");

    // Verify that gameRepository methods were called
    verify(gameRepository, times(1)).findById(anyString());

    // Assert that the returned game is not null
    assertEquals("test", result);
  }

  @Test
  void resetGameTest() {
    // Create a new game
    when(gameRepository.findById(game.getId())).thenReturn(game);

    Game result = gameServiceImpl.resetGame("sampleId");

    // Verify that gameRepository methods were called
    verify(gameRepository, times(1)).findById(anyString());
    verify(gameRepository, times(1)).save(any(Game.class));

    // Assert that the returned game is not null
    assertEquals(7, result.getAttemptsLeft());
    assertEquals("sampleId", result.getId());
    assertEquals("", result.getLetters());
  }
}
