package com.game_classes.models.dto;

import java.util.List;
import org.springframework.data.domain.Page;
import com.game_classes.interfaces.modelInterfaces.UserRankData;

public class UserProfileDto {
  List<Integer> statusValues;
  Page<UserRankData> userRankDatas;
  Page<UserRankData> userRankDataPage;

  int winCount = 0;
  int lossCount = 0;

  public UserProfileDto(
      List<Integer> statusValues,
      Page<UserRankData> userRankDatas,
      Page<UserRankData> userRankDataPage,
      int winCount,
      int lossCount) {
    this.statusValues = statusValues;
    this.userRankDatas = userRankDatas;
    this.userRankDataPage = userRankDataPage;
    this.winCount = winCount;
    this.lossCount = lossCount;
  }

  public List<Integer> getStatusValues() {
    return statusValues;
  }

  public void setStatusValues(List<Integer> statusValues) {
    this.statusValues = statusValues;
  }

  public Page<UserRankData> getUserRankDatas() {
    return userRankDatas;
  }

  public void setUserRankDatas(Page<UserRankData> userRankDatas) {
    this.userRankDatas = userRankDatas;
  }

  public Page<UserRankData> getUserRankDataPage() {
    return userRankDataPage;
  }

  public void setUserRankDataPage(Page<UserRankData> userRankDataPage) {
    this.userRankDataPage = userRankDataPage;
  }

  public int getWinCount() {
    return winCount;
  }

  public void setWinCount(int winCount) {
    this.winCount = winCount;
  }

  public int getLossCount() {
    return lossCount;
  }

  public void setLossCount(int lossCount) {
    this.lossCount = lossCount;
  }
}