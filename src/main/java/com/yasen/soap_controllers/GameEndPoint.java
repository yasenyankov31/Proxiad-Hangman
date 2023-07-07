package com.yasen.soap_controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.game_classes.interfaces.services.RankingService;
import com.game_classes.soap_models.RankingDataSoap;
import com.game_classes.soap_models.RankingDataSoapList;

@Endpoint
public class GameEndPoint {

	private static final String NAMESPACE = "http://www.game_classes.com/soap-models";
	@Autowired
	private RankingService rankingService;

	@PayloadRoot(namespace = NAMESPACE, localPart = "RankingRequest")
	@ResponsePayload
	public RankingDataSoapList getLoanStatus() {

		RankingDataSoapList list = new RankingDataSoapList();

		List<String> userAllTime = new ArrayList<>();
		List<Integer> winAllTime = new ArrayList<>();

		List<String> userForMonth = new ArrayList<>();
		List<Integer> winsForMonth = new ArrayList<>();

		rankingService.topTenOfAllTime().forEach(item -> {
			userAllTime.add(item.getUsername());
			winAllTime.add(item.getWinCount());
		});

		rankingService.topTenOfTheMonth().forEach(item -> {
			userForMonth.add(item.getUsername());
			winsForMonth.add(item.getWinCount());
		});

		RankingDataSoap monthData = new RankingDataSoap();
		monthData.setRankType("monthly");
		monthData.setUserNames(userForMonth);
		monthData.setWinCounts(winsForMonth);

		RankingDataSoap allTimeData = new RankingDataSoap();
		allTimeData.setRankType("allTime");
		allTimeData.setUserNames(userAllTime);
		allTimeData.setWinCounts(winAllTime);

		list.getRankingDataSoap().add(monthData);
		list.getRankingDataSoap().add(allTimeData);

		return list;
	}

}
