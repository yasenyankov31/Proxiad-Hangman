package com.game_classes.servicesImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.game_classes.interfaces.jpaRepositories.CompletedGameRepository;
import com.game_classes.interfaces.jpaRepositories.RankingRepository;
import com.game_classes.interfaces.jpaRepositories.UserRepository;
import com.game_classes.interfaces.modelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.models.GameStatus;
import com.game_classes.models.UserData;
import com.game_classes.models.game.CompletedGame;
import com.game_classes.models.game.Game;
import com.game_classes.models.game.RankingPerGamer;

@Service
public class RankingServiceImpl implements RankingService {
	@Autowired
	private RankingRepository rankingRepository;

	@Autowired
	private CompletedGameRepository completedGameRepository;

	@Autowired
	private UserRepository userRepository;

	UserData checkIfUserExist(String username) {
		List<UserData> users = userRepository.findAllByUsername(username);

		if (users.size() == 0) {
			UserData newUser = new UserData();
			newUser.setUsername(username);
			userRepository.save(newUser);
			return newUser;
		}
		return users.get(0);
	}

	@Override
	public void completeGame(Game game, String username) {
		GameStatus gameResultState = game.getGameState();
		UserData user = checkIfUserExist(username);
		String status = gameResultState == GameStatus.Lost ? "Lost" : "Won";
		CompletedGame completedGame = new CompletedGame(status, game);
		RankingPerGamer statistic = new RankingPerGamer(user, completedGame);
		rankingRepository.save(statistic);
		completedGame.setRankingPerGamer(statistic);
		completedGameRepository.save(completedGame);
	}

	@Override
	public List<TopPlayerStats> topTenOfTheMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		Date thirtyDaysBefore = calendar.getTime();
		Pageable pageable = PageRequest.of(0, 10);

		return completedGameRepository.findTop10(thirtyDaysBefore, pageable);
	}

	@Override
	public List<TopPlayerStats> topTenOfAllTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1);

		Date date = calendar.getTime();
		Pageable pageable = PageRequest.of(0, 10);
		return completedGameRepository.findTop10(date, pageable);
	}

	@Override
	public Page<UserRankData> getUserInfo(String username, Integer pageNum) {
		if (pageNum == null) {
			return completedGameRepository.userProfileData(username, null);
		}
		Pageable pageable = PageRequest.of(pageNum, 5);
		Page<UserRankData> userDatas = completedGameRepository.userProfileData(username, pageable);

		return userDatas;
	}
}
