package com.game_classes.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class RankingPerGamer {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private long id;

  @OneToOne(cascade = CascadeType.ALL)
  private User user;

  @OneToMany(mappedBy = "rankingPerGamer")
  private List<CompletedGame> completedGames = new ArrayList<>();

  public RankingPerGamer(User user, CompletedGame completedGame) {
    super();
    this.user = user;
    this.completedGames.add(completedGame);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<CompletedGame> getCompletedGame() {
    return completedGames;
  }

  public void setCompletedGame(List<CompletedGame> completedGames) {
    this.completedGames = completedGames;
  }
}
