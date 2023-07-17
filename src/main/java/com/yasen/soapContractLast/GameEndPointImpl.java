package com.yasen.soapContractLast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.models.game.RankingData;
import com.yasen.soapContractLast.soap_models.GetUserRankingDataException;
import com.yasen.soapContractLast.soap_models.UserProfileDtoCL;
import com.yasen.soapContractLast.soap_models.UserRankDataDtoCL;

@Endpoint
@WebService(endpointInterface = "com.yasen.soapContractLast.GameEndPoint")
public class GameEndPointImpl implements GameEndPoint {

  private final RankingService rankingService;

  @Autowired
  public GameEndPointImpl(RankingService rankingService) {
    this.rankingService = rankingService;
  }

  @Autowired private UserService userService;

  @Override
  @WebMethod
  public Map<String, RankingData> getAllRankingData() {
    RankingData allTimeData = new RankingData();
    RankingData monthData = new RankingData();

    rankingService
        .topTenOfAllTime()
        .forEach(
            item -> {
              allTimeData.getUserNames().add(item.getUsername());
              allTimeData.getWinCounts().add(item.getWinCount());
            });

    rankingService
        .topTenOfTheMonth()
        .forEach(
            item -> {
              monthData.getUserNames().add(item.getUsername());
              monthData.getWinCounts().add(item.getWinCount());
            });

    Map<String, RankingData> responseData = new HashMap<>();
    responseData.put("allTime", allTimeData);
    responseData.put("month", monthData);

    return responseData;
  }

  @Override
  @WebMethod
  public UserProfileDtoCL getRankingUserData(String username, Integer pageNum)
      throws GetUserRankingDataException {
    if (!userService.checkIfUserExist(username) || username.isBlank() || username.isEmpty()) {
      Throwable t = new IllegalArgumentException("Invalid name");
      throw new GetUserRankingDataException("Error ocured.Check the username or the pageNum", t);
    }

    pageNum = pageNum != null ? pageNum : 0;
    int winCount = 0;
    int lossCount = 0;
    int prevWinCount = 0;
    List<Integer> statusValues = new ArrayList<>();
    Page<UserRankData> userRankDatas = rankingService.getUserInfo(username, pageNum);
    Page<UserRankData> userRankProgressAllTime = rankingService.getUserInfo(username, null);

    for (UserRankData rankData : userRankProgressAllTime) {
      if (rankData.getGameStatus().equals("Won")) {
        winCount++;
        statusValues.add(++prevWinCount);
      } else {
        lossCount++;
        statusValues.add(0);
        prevWinCount = 0;
      }
    }

    List<UserRankDataDtoCL> castedList = new ArrayList<>();

    for (UserRankData rankData : userRankDatas.getContent()) {
      castedList.add(new UserRankDataDtoCL(rankData));
    }

    return new UserProfileDtoCL(statusValues, castedList, winCount, lossCount);
  }
}
