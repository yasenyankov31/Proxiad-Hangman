package com.yasen.api_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game_classes.interfaces.GameFactory;
import com.game_classes.interfaces.Services.GameService;
import com.game_classes.interfaces.Services.RankingService;
import com.game_classes.models.Dto.GameDto;
import com.game_classes.models.Game.Game;
import com.game_classes.models.Game.RankingData;

@RestController
@RequestMapping("/api/ranking")
public class GameRestController {
	@Autowired
	private GameService gameService;

	@Autowired
	private RankingService rankingService;

	@Autowired
	private GameFactory gameFactory;

	@GetMapping("/")
	public Map<String, RankingData> getRankingData() {
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

		RankingData allTimeData = new RankingData(userAllTime, winAllTime);
		RankingData monthData = new RankingData(userForMonth, winsForMonth);

		Map<String, RankingData> responseData = new HashMap<>();
		responseData.put("allTime", allTimeData);
		responseData.put("month", monthData);

		return responseData;
	}

	@GetMapping("/notCompletedGames/{pageNum}")
	public ResponseEntity<List<GameDto>> getNotCompletedGames(@PathVariable Integer pageNum) {
		if (pageNum == null) {
			pageNum = 0;
		}
		Page<Game> unfinishedGames = gameService.getUnfinishedGames(pageNum);

		return ResponseEntity.ok().body(gameFactory.fromEntities(unfinishedGames.getContent()));
	}

	@GetMapping("/endingResult/{gameId}/{username}")
	public ResponseEntity<String> getEndingResult(@PathVariable long gameId, @PathVariable String username) {
		if (gameId == 0) {
			return ResponseEntity.badRequest().body("Not existing game!");
		}
		if (username.isBlank() || username.isEmpty()) {
			return ResponseEntity.badRequest().body("Write at least one character for Username!");
		}
		Game game = gameService.getGameState(gameId);
		String gameInfo = game.getInfo();
		rankingService.completeGame(game, username);
		return ResponseEntity.ok().body(gameInfo);
	}

	@PostMapping("/new")
	public ResponseEntity<GameDto> createNewGame() {
		Game newGame = gameService.createNewGame();
		GameDto gameDto = gameFactory.fromEntity(newGame);
		gameDto.setGameOver(false);
		return ResponseEntity.ok().body(gameDto);
	}

	@PostMapping("/guess/{gameId}/{letter}")
	public ResponseEntity<GameDto> guessLetter(@PathVariable long gameId, @PathVariable Character letter) {
		Game game = gameService.guessLetter(gameId, letter);
		GameDto gameDto = gameFactory.fromEntity(game);
		if (game.getAttemptsLeft() < 0) {
			game = gameService.getGameState(letter);
		}

		if (game == null) {
			return ResponseEntity.badRequest().body(null);
		}

		String gameInfo = game.getInfo();
		boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won");

		if (isGameOver) {
			return ResponseEntity.ok().body(gameDto);
		}

		game.setLettersUsed(gameService.getUsersLetters(gameId));
		game.setWordNum(game.getWordNum());

		return ResponseEntity.ok().body(gameDto);
	}

	@PostMapping("/reset/{gameId}")
	public ResponseEntity<GameDto> resetGame(@PathVariable long gameId) {
		Game game = gameService.getGameState(gameId);

		if (game == null) {
			return ResponseEntity.badRequest().body(null);
		}

		gameService.resetGame(gameId);
		game.setLettersUsed(gameService.getUsersLetters(gameId));
		game.setWordNum(game.getWordNum());

		return ResponseEntity.ok().body(gameFactory.fromEntity(game));
	}

	@PostMapping("/getGame/{gameId}")
	public ResponseEntity<GameDto> getGameById(@PathVariable long gameId) {
		Game game = gameService.getGameState(gameId);

		if (game == null) {
			return ResponseEntity.badRequest().body(null);
		}

		String gameInfo = game.getInfo();
		boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won")
				|| game.getAttemptsLeft() <= 0;

		game.setLettersUsed(gameService.getUsersLetters(gameId));
		game.setWordNum(game.getWordNum());
		GameDto gameDto = gameFactory.fromEntity(game);
		gameDto.setGameOver(isGameOver);

		return ResponseEntity.ok().body(gameDto);
	}
}
