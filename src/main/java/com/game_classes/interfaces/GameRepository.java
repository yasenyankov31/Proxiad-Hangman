package com.game_classes.interfaces;

import com.game_classes.models.Game;

public interface GameRepository {
  void createGame(Game game);

  void updateGame(Game game);

  void deleteGame(Game game);

  Game findById(long gameId);

  void addToQueue(long id);

  long getFromQueue();
}
