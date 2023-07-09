package com.game_classes.interfaces.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.game_classes.interfaces.modelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.models.game.Game;

public interface RankingService {
	public void completeGame(Game game, String username);

	List<TopPlayerStats> topTenOfAllTime();

	List<TopPlayerStats> topTenOfTheMonth();

	Page<UserRankData> getUserInfo(String username, Integer pageNum);
}
