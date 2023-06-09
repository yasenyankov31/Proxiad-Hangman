package com.game_classes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import com.game_classes.interfaces.GameRepository;
import com.game_classes.models.Game;

@Component
public class InMemoryGameRepository implements GameRepository {

  private Map<String, Game> games = new ConcurrentHashMap<>();

  @Override
  public void save(Game game) {
    games.put(game.getId(), game);
  }

  @Override
  public Game findById(String gameId) {
    return games.get(gameId);
  }
}
