package com.game_classes.interfaces;

import java.util.List;
import com.game_classes.models.Game;
import com.game_classes.models.User;

public interface RankingService {
  public void completeGame(Game game, User user);

  List<Game> topTenOfAllTime();

  List<Game> topTenOfTheMonth();
}
