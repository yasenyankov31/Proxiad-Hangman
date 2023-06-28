package com.game_classes.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.game_classes.interfaces.ModelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.ModelInterfaces.UserRankData;
import com.game_classes.models.Game;

public interface RankingService {
	public void completeGame(Game game, String username);

	List<TopPlayerStats> topTenOfAllTime();

	List<TopPlayerStats> topTenOfTheMonth();

	Page<UserRankData> getUserInfo(String username, Integer pageNum);

}
