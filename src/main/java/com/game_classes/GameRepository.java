package com.game_classes;

public interface GameRepository {
  void save(Game game);

  Game findById(String gameId);
}
