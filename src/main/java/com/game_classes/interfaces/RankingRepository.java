package com.game_classes.interfaces;

import com.game_classes.models.CompletedGame;
import com.game_classes.models.RankingPerGamer;
import com.game_classes.models.User;

public interface RankingRepository {
  public void createUser(User user);

  public void createStatisticRecord(CompletedGame completedGame, RankingPerGamer gameStatistic);
}
