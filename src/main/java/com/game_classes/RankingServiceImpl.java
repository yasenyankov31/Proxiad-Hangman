package com.game_classes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.game_classes.interfaces.RankingService;
import com.game_classes.interfaces.JpaRepositories.CompletedGameRepository;
import com.game_classes.interfaces.JpaRepositories.RankingRepository;
import com.game_classes.interfaces.JpaRepositories.UserRepository;
import com.game_classes.interfaces.ModelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.ModelInterfaces.UserRankData;
import com.game_classes.models.CompletedGame;
import com.game_classes.models.Game;
import com.game_classes.models.RankingPerGamer;
import com.game_classes.models.User;

@Service
public class RankingServiceImpl implements RankingService {
	@Autowired
	private RankingRepository rankingRepository;

	@Autowired
	private CompletedGameRepository completedGameRepository;

	@Autowired
	private UserRepository userRepository;

	User checkIfUserExist(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			User new_user = new User();
			new_user.setUsername(username);
			userRepository.save(new_user);
			return new_user;
		}
		return user;
	}

	@Override
	public void completeGame(Game game, String username) {
		String gameInfo = game.getInfo();
		User user = checkIfUserExist(username);
		String status = gameInfo.contains("Game over") ? "Lost" : "Won";
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
	public List<UserRankData> getUserInfo(String username) {
		return completedGameRepository.UserProfileData(username);
	}

}
