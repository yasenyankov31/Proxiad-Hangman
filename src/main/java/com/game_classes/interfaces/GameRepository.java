package com.game_classes.interfaces;

import com.game_classes.models.Game;

public interface GameRepository {
  void save(Game game);

  Game findById(String gameId);
}
