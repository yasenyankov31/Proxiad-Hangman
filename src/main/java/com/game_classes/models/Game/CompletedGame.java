package com.game_classes.models.Game;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CompletedGame {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private long id;

  private String gameStatus;

  @OneToOne private Game game;

  @ManyToOne private RankingPerGamer rankingPerGamer;

  private Date finishDate;

  public CompletedGame() {}

  public CompletedGame(String gameStatus, Game game) {
    super();
    this.game = game;
    this.gameStatus = gameStatus;
    Calendar today = Calendar.getInstance();
    this.finishDate = today.getTime();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getGameStatus() {
    return gameStatus;
  }

  public void setGameStatus(String gameStatus) {
    this.gameStatus = gameStatus;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public RankingPerGamer getRankingPerGamer() {
    return rankingPerGamer;
  }

  public void setRankingPerGamer(RankingPerGamer rankingPerGamer) {
    this.rankingPerGamer = rankingPerGamer;
  }

  public Date getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(Date finishDate) {
    this.finishDate = finishDate;
  }
}
