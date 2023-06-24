package com.game_classes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game_classes.interfaces.JpaDataService;
import com.game_classes.interfaces.RankingRepository;
import com.game_classes.interfaces.RankingService;
import com.game_classes.models.CompletedGame;
import com.game_classes.models.Game;
import com.game_classes.models.RankingPerGamer;
import com.game_classes.models.User;

@Service
public class RankingServiceImpl implements RankingService {
  @Autowired private RankingRepository gameRepository;

  @Autowired private JpaDataService jpaDataService;

  @Override
  public void completeGame(Game game, User user) {

    String gameInfo = game.getInfo();
    boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won");
    if (isGameOver) {
      gameRepository.createUser(user);
      String status = gameInfo.contains("Game over") ? "Lost" : "Won";
      CompletedGame completedGame = new CompletedGame(status, game);
      RankingPerGamer statistic = new RankingPerGamer(user, completedGame);
      completedGame.setRankingPerGamer(statistic);
      gameRepository.createStatisticRecord(completedGame, statistic);
    }
  }

  @Override
  public List<Game> topTenOfAllTime() {
    List<Long> top10WinGameIds = jpaDataService.allWinsOfAllPlayers();
    System.out.println(top10WinGameIds);
    return null;
  }

  @Override
  public List<Game> topTenOfTheMonth() {
    // TODO Auto-generated method stub
    return null;
  }
}
