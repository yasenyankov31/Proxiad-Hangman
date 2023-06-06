package com.game_classes;

public interface GameService {
  Game createNewGame();

  Game guessLetter(String gameId, char letter);

  Game getGameState(String gameId);

  Game resetGame(String gameId);

  String getRandomWord();

  String getUsersLetters(String gameId);
}
