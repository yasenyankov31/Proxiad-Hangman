package com.yasen.soap_controllers;

import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.soap_models.RankingDataSoap;
import com.game_classes.soap_models.RankingDataSoapList;
import com.game_classes.soap_models.SoapException;
import com.game_classes.soap_models.UserProfileSoap;
import com.game_classes.soap_models.UserRankDataList;
import com.game_classes.soap_models.UserRankDataSoap;
import com.game_classes.soap_models.UserRankingRequest;

@Endpoint
public class StatisticsEndPoint {

	private static final String NAMESPACE = "http://www.game_classes.com/soap-models";
	@Autowired
	private RankingService rankingService;

	@Autowired
	private UserService userService;

	@PayloadRoot(namespace = NAMESPACE, localPart = "RankingRequest")
	@ResponsePayload
	public RankingDataSoapList getRankingGameData() {

		RankingDataSoapList list = new RankingDataSoapList();

		RankingDataSoap monthData = new RankingDataSoap();
		RankingDataSoap allTimeData = new RankingDataSoap();

		monthData.setRankType("monthly");

		allTimeData.setRankType("allTime");

		rankingService.topTenOfAllTime().forEach(item -> {
			allTimeData.getUserNames().add(item.getUsername());
			allTimeData.getWinCounts().add(item.getWinCount());
		});

		rankingService.topTenOfTheMonth().forEach(item -> {
			monthData.getUserNames().add(item.getUsername());
			monthData.getWinCounts().add(item.getWinCount());
		});

		list.getRankingDataSoap().add(monthData);
		list.getRankingDataSoap().add(allTimeData);

		return list;
	}

	@PayloadRoot(namespace = NAMESPACE, localPart = "UserRankingRequest")
	@ResponsePayload
	public UserProfileSoap getRankingUserData(@RequestPayload UserRankingRequest request)
			throws DatatypeConfigurationException, SoapException {
		UserProfileSoap userProfileSoap = new UserProfileSoap();
		UserRankDataList userRankDataList = new UserRankDataList();
		Integer pageNum = request.getPageNum();
		String username = request.getUsername();
		if (!userService.checkIfUserExist(username)) {
			throw new SoapException("Invalid username.");
		}

		int winCount = 0;
		int lossCount = 0;
		int prevWinCount = 0;

		Page<UserRankData> userRankDataPaged = rankingService.getUserInfo(username, pageNum);
		for (UserRankData userRankData : userRankDataPaged) {
			UserRankDataSoap dataSoap = new UserRankDataSoap(userRankData);
			userRankDataList.getUserRankDataList().add(dataSoap);
		}

		Page<UserRankData> userRankProgressAllTime = rankingService.getUserInfo(username, null);

		for (UserRankData rankData : userRankProgressAllTime) {
			if (rankData.getGameStatus().equals("Won")) {
				winCount++;
				userProfileSoap.getStatusValues().add(++prevWinCount);
			} else {
				lossCount++;
				userProfileSoap.getStatusValues().add(0);
				prevWinCount = 0;
			}
		}
		userProfileSoap.setUserRankDataPaged(userRankDataList);
		userProfileSoap.setLossCount(lossCount);
		userProfileSoap.setWinCount(winCount);

		return userProfileSoap;

	}

}
