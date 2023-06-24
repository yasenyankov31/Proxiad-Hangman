package com.game_classes.interfaces;

import com.game_classes.models.Game;

public interface GameService {
  Game createNewGame();

  Game guessLetter(long gameId, char letter);

  Game getGameState(long gameId);

  Game resetGame(long gameId);

  String getRandomWord();

  String getUsersLetters(long gameId);

  void addOponentId(long id, Game userGame);

  void addToQueue(Game game);

  long getFromQueue();
}
