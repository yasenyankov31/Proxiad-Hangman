package com.yasen.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.game_classes.GameFactoryImpl;
import com.game_classes.interfaces.GameFactory;
import com.game_classes.interfaces.GameRepository;
import com.game_classes.interfaces.modelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.services.GameService;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.models.game.Game;
import com.yasen.api_controllers.GameRestApiController;
import com.yasen.soap.TopPlayerStatsTestClass;

public class GameControllerTest {
	@Mock
	private GameRepository repository;

	@Mock
	private GameService gameService;

	@Mock
	private RankingService rankingService;

	@Mock
	private GameFactory gameFactory;

	@InjectMocks
	private GameRestApiController controller;

	private MockMvc mockMvc;

	private Game game;
	long gameId = 2830948093284L;

	@BeforeEach
	public void setup() {
		game = new Game(gameId, "example", "*******", 7, 2);
		game.setLettersUsed("a");
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetRankingData() throws Exception {
		// Mock data
		Date date = new Date(0);
		TopPlayerStatsTestClass rankingItem1 = new TopPlayerStatsTestClass("user1", 10, 2, date);
		List<TopPlayerStats> allTimeRanking = new ArrayList<>();
		allTimeRanking.add(rankingItem1);

		TopPlayerStatsTestClass rankingItem2 = new TopPlayerStatsTestClass("user2", 20, 3, date);
		List<TopPlayerStats> monthRanking = new ArrayList<>();
		monthRanking.add(rankingItem2);

		when(rankingService.topTenOfAllTime()).thenReturn(allTimeRanking);
		when(rankingService.topTenOfTheMonth()).thenReturn(monthRanking);

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/ranking/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.month.userNames[0]").value("user2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.month.winCounts[0]").value(20))
				.andExpect(MockMvcResultMatchers.jsonPath("$.allTime.userNames[0]").value("user1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.allTime.winCounts[0]").value(10));

		// Verify the interactions with the services
		verify(rankingService, times(1)).topTenOfAllTime();
		verify(rankingService, times(1)).topTenOfTheMonth();

	}

	@Test
	public void testGetNotCompletedGames() throws Exception {
		List<Game> games = new ArrayList<>();
		GameFactory testFactory = new GameFactoryImpl();

		Game game = new Game(12345L, "word", "w**d", 7, 7);
		games.add(game);

		Page<Game> unfinishedGames = new PageImpl<>(games);

		when(gameService.getUnfinishedGames(ArgumentMatchers.anyInt())).thenReturn(unfinishedGames);
		when(gameFactory.fromEntities(games)).thenReturn(testFactory.fromEntities(games));

		// Perform the GET request
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/ranking/notCompletedGames/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(game.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].guessedWord").value(game.getGuessedWord()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].lettersUsed").value(game.getLetters()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].attemptsLeft").value(game.getAttemptsLeft()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].wordNum").value(game.getWordNum()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(game.getDate()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gameOver").value(false));

		// Verify the interactions with the services
		verify(gameFactory, times(1)).fromEntities(games);

	}

//	@Test
//	public void testGetEndingResult() throws Exception {
//		// Mock data
//		long gameId = 1;
//		String username = "user1";
//		Game game = new Game();
//
//		when(gameService.getGameState(gameId)).thenReturn(game);
//
//		// Perform the GET request
//		mockMvc.perform(MockMvcRequestBuilders.get("/endingResult/1/user1").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().string("Game info"));
//
//		// Verify the interactions with the services
//		verify(gameService, times(1)).getGameState(gameId);
//		verify(rankingService, times(1)).completeGame(game, username);
//
//	}
}
