package com.game_classes.interfaces;

import com.game_classes.models.Game;

public interface GameService {
  Game createNewGame();

  Game guessLetter(String gameId, char letter);

  Game getGameState(String gameId);

  Game resetGame(String gameId);

  String getRandomWord();

  String getUsersLetters(String gameId);

  void addOponentId(String id, Game userGame);

  void addToQueue(Game game);

  String getFromQueue();
}
