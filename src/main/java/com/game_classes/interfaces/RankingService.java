package com.game_classes.interfaces;

import java.util.List;

import com.game_classes.interfaces.ModelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.ModelInterfaces.UserRankData;
import com.game_classes.models.Game;

public interface RankingService {
	public void completeGame(Game game, String username);

	List<TopPlayerStats> topTenOfAllTime();

	List<TopPlayerStats> topTenOfTheMonth();

	List<UserRankData> getUserInfo(String username);

}
