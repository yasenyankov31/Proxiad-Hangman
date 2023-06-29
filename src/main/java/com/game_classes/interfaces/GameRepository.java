package com.game_classes.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.game_classes.models.Game.Game;

public interface GameRepository {
  void createGame(Game game);

  void updateGame(Game game);

  void deleteGame(Game game);

  Game findById(long gameId);

  void addToQueue(long id);

  long getFromQueue();

  Page<Game> getUnfinishedGames(Pageable pageable);
}
