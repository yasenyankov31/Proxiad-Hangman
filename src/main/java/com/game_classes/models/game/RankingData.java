package com.game_classes.models.game;

import java.util.List;

public class RankingData {
  private List<String> userNames;
  private List<Integer> winCounts;

  public RankingData(List<String> userNames, List<Integer> winCounts) {
    this.userNames = userNames;
    this.winCounts = winCounts;
  }

  public List<String> getUserNames() {
    return userNames;
  }

  public void setUserNames(List<String> userNames) {
    this.userNames = userNames;
  }

  public List<Integer> getWinCounts() {
    return winCounts;
  }

  public void setWinCounts(List<Integer> winCounts) {
    this.winCounts = winCounts;
  }
}
