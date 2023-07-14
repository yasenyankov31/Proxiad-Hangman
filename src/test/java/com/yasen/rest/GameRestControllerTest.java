package com.yasen.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

class GameRestControllerTest {
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

	GameFactory testFactory = new GameFactoryImpl();

	private Game game;

	String apiPath = "/api/gameController";

	@BeforeEach
	public void setup() {
		game = new Game(12345L, "w**d", "word", 7, 1);
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetRankingData() throws Exception {
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
		mockMvc.perform(MockMvcRequestBuilders.get(apiPath + "/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.month.userNames[0]").value("user2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.month.winCounts[0]").value(20))
				.andExpect(MockMvcResultMatchers.jsonPath("$.allTime.userNames[0]").value("user1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.allTime.winCounts[0]").value(10));

		// Verify the interactions with the services
		verify(rankingService, times(1)).topTenOfAllTime();
		verify(rankingService, times(1)).topTenOfTheMonth();
	}

//  @Test
//  void testGetNotCompletedGames() throws Exception {
//    List<Game> games = new ArrayList<>();
//    games.add(game);
//
//    Page<Game> unfinishedGames = new PageImpl<>(games);
//
//    when(gameService.getUnfinishedGames(ArgumentMatchers.anyInt())).thenReturn(unfinishedGames);
//    when(gameFactory.fromEntities(games)).thenReturn(testFactory.fromEntities(games));
//
//    // Perform the GET request
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.get(apiPath + "/notCompletedGames/0")
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(MockMvcResultMatchers.status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(game.getId()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$[0].guessedWord").value(game.getGuessedWord()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$[0].lettersUsed").value(game.getLetters()))
//        .andExpect(
//            MockMvcResultMatchers.jsonPath("$[0].attemptsLeft").value(game.getAttemptsLeft()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$[0].wordNum").value(game.getWordNum()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(game.getDate()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$[0].gameOver").value(false));
//
//    // Verify the interactions with the services
//    verify(gameFactory, times(1)).fromEntities(games);
//  }

	@Test
	void testGetEndingResult() throws Exception {
		// Mock data
		String username = "user1";

		when(gameService.getGameState(game.getId())).thenReturn(game);

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get(apiPath + "/endingResult/12345/user1")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(
						"Guessed word: " + game.getGuessedWord() + " , Attempts left: " + game.getAttemptsLeft()));

		// Verify the interactions with the services
		verify(gameService, times(1)).getGameState(game.getId());
		verify(rankingService, times(1)).completeGame(game, username);
	}

	@Test
  void testCreateNewGame() throws Exception {

    when(gameService.createNewGame()).thenReturn(game);

    // Mock the behavior of gameFactory.fromEntity()

    when(gameFactory.fromEntity(game)).thenReturn(testFactory.fromEntity(game));

    // Perform the MVC request and validate the response
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(apiPath + "/new").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.guessedWord").value(game.getGuessedWord()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lettersUsed").value(game.getLetters()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.attemptsLeft").value(game.getAttemptsLeft()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.wordNum").value(game.getWordNum()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(game.getDate()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.gameOver").value(false));

    // Verify that the necessary methods were called
    verify(gameService, times(1)).createNewGame();
    verify(gameFactory, times(1)).fromEntity(game);
  }

	@Test
	void testGuessLetter() throws Exception {
		// Mock the behavior of gameService.guessLetter()

		Character letter = 'a';

		when(gameService.guessLetter(game.getId(), letter)).thenReturn(game);

		when(gameFactory.fromEntity(game)).thenReturn(testFactory.fromEntity(game));
		when(gameService.getUsersLetters(game.getId())).thenReturn(letter + "");

		// Perform the MVC request and validate the response
		mockMvc.perform(MockMvcRequestBuilders.post(apiPath + "/guess/" + game.getId() + "/" + letter)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.guessedWord").value(game.getGuessedWord()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lettersUsed").value(letter + ""))
				.andExpect(MockMvcResultMatchers.jsonPath("$.attemptsLeft").value(game.getAttemptsLeft()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.wordNum").value(game.getWordNum()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date").value(game.getDate()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gameOver").value(false));

		// Verify that the necessary methods were called
		verify(gameService, times(1)).guessLetter(game.getId(), letter);
		verify(gameFactory, times(1)).fromEntity(game);
	}

	@Test
	void testResetGame() throws Exception {
		Long gameId = game.getId();
		when(gameService.getGameState(gameId)).thenReturn(game);
		// Mock the behavior of gameFactory.fromEntity()

		when(gameFactory.fromEntity(game)).thenReturn(testFactory.fromEntity(game));

		// Perform the MVC request and validate the response
		mockMvc.perform(
				MockMvcRequestBuilders.post(apiPath + "/reset/" + gameId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.guessedWord").value(game.getGuessedWord()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lettersUsed").value(""))
				.andExpect(MockMvcResultMatchers.jsonPath("$.attemptsLeft").value(game.getAttemptsLeft()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.wordNum").value(game.getWordNum()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date").value(game.getDate()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gameOver").value(false));

		// Verify that the necessary methods were called
		verify(gameService, times(1)).getGameState(gameId);
		verify(gameService, times(1)).resetGame(gameId);
		verify(gameService, times(1)).getUsersLetters(gameId);
		verify(gameFactory, times(1)).fromEntity(game);
	}

	@Test
	void testGetGameById() throws Exception {
		// Mock the behavior of gameService.getGameState()
		long gameId = game.getId();
		when(gameService.getGameState(gameId)).thenReturn(game);

		when(gameFactory.fromEntity(game)).thenReturn(testFactory.fromEntity(game));

		// Perform the MVC request and validate the response
		mockMvc.perform(
				MockMvcRequestBuilders.get(apiPath + "/getGame/" + gameId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.guessedWord").value(game.getGuessedWord()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lettersUsed").value(""))
				.andExpect(MockMvcResultMatchers.jsonPath("$.attemptsLeft").value(game.getAttemptsLeft()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.wordNum").value(game.getWordNum()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date").value(game.getDate()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gameOver").value(false));

		// Verify that the necessary methods were called
		verify(gameService, times(1)).getGameState(gameId);
		verify(gameService, times(1)).getUsersLetters(gameId);
		verify(gameFactory, times(1)).fromEntity(game);
	}
}
