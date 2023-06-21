package com.game_classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import com.game_classes.interfaces.GameRepository;
import com.game_classes.models.Game;

@Repository
public class InMemoryGameRepository implements GameRepository {

  private Map<String, Game> games = new ConcurrentHashMap<>();

  private List<String> queue = new ArrayList<>();

  @Override
  public void save(Game game) {
    games.put(game.getId(), game);
  }

  @Override
  public Game findById(String gameId) {
    return games.get(gameId);
  }

  @Override
  public void addToQueue(String id) {
    queue.add(id);
  }

  @Override
  public String getFromQueue() {
    if (queue.isEmpty()) {
      return null;
    }
    String id = queue.get(0);
    queue.remove(0);
    return id;
  }
}
