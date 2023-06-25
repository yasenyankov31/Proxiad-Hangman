package com.game_classes.interfaces.JpaRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.game_classes.interfaces.ModelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.ModelInterfaces.UserRankData;
import com.game_classes.models.CompletedGame;

public interface CompletedGameRepository extends CrudRepository<CompletedGame, Long> {
	@Query("SELECT u.username as username, " + "SUM(CASE WHEN cg.gameStatus = 'Won' THEN 1 ELSE 0 END) AS winCount, "
			+ "SUM(CASE WHEN cg.gameStatus = 'Lost' THEN 1 ELSE 0 END) AS lostCount,cg.finishDate "
			+ "FROM CompletedGame cg "
			+ "JOIN RankingPerGamer rpg ON rpg.id = cg.rankingPerGamer AND cg.finishDate >= :date "
			+ "JOIN User u ON u.id = rpg.user " + "GROUP BY u.username " + "ORDER BY winCount DESC")

	List<TopPlayerStats> findTop10(Date date, Pageable pageable);

	@Query("SELECT cg.gameStatus as gameStatus,g.word as word,g.lettersUsed as lettersUsed,g.attemptsLeft as attemptsLeft,g.date as startDate "
			+ "FROM CompletedGame cg " + "JOIN RankingPerGamer rpg ON rpg.id = cg.rankingPerGamer "
			+ "JOIN User u ON u.id = rpg.user "
			+ "JOIN Game g ON g.id = cg.game WHERE u.username = :username ORDER BY cg.finishDate ")
	List<UserRankData> UserProfileData(String username);
}
