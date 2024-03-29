package com.yasen.mvc_controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.game_classes.interfaces.services.GameService;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.models.GameStatus;
import com.game_classes.models.SubmitForm;
import com.game_classes.models.game.Game;

@Controller
public class GameController {

  @Autowired
  private GameService gameService;

  @Autowired
  private RankingService rankingService;

  @RequestMapping("/")
  public ModelAndView index() {

    List<String> userAllTime = new ArrayList<>();
    List<Integer> winAllTime = new ArrayList<>();

    List<String> userForMonth = new ArrayList<>();
    List<Integer> winsForMonth = new ArrayList<>();

    rankingService.topTenOfAllTime().forEach(item -> {
      userAllTime.add('"' + item.getUsername() + '"');
      winAllTime.add(item.getWinCount());
    });
    rankingService.topTenOfTheMonth().forEach(item -> {
      userForMonth.add('"' + item.getUsername() + '"');
      winsForMonth.add(item.getWinCount());
    });

    ModelAndView modelAndView = new ModelAndView("game/index");
    modelAndView.addObject("userAllTime", userAllTime);
    modelAndView.addObject("winAllTime", winAllTime);

    modelAndView.addObject("userForMonth", userForMonth);
    modelAndView.addObject("winsForMonth", winsForMonth);

    return modelAndView;
  }

  @RequestMapping("/notCompletedGames")
  public ModelAndView notCompletedGames(Integer pageNum) {
    ModelAndView modelAndView = new ModelAndView("game/not_completed_games");
    if (pageNum == null) {
      pageNum = 0;
    }
    Page<Game> unfinishedGames = gameService.getUnfinishedGames(pageNum);
    modelAndView.addObject("unfinishedGames", unfinishedGames);
    modelAndView.addObject("pageNum", pageNum);

    return modelAndView;
  }

  @RequestMapping("/endingResult")
  public ModelAndView endingResult(String username, Long gameId) {
    if (username.isBlank() || username.isEmpty()) {
      return new ModelAndView("error").addObject("errorMessage",
          "Write at least one character for UserDataname! ");
    }
    Game game = gameService.getGameState(gameId);
    ModelAndView modelAndView = new ModelAndView("game/game");
    GameStatus gameState = game.getGameState();

    boolean isGameOver = gameState == GameStatus.Lost || gameState == GameStatus.Won;
    rankingService.completeGame(game, username);
    modelAndView.addObject("gameId", game.getId());
    modelAndView.addObject("result", gameState.toString());
    modelAndView.addObject("lettersUsed", game.getLetters());
    modelAndView.addObject("wordNum", game.getWordNum());

    modelAndView.addObject("isGameOver", isGameOver);
    return modelAndView;
  }

  @RequestMapping("/createParty")
  public ModelAndView createParty() {
    ModelAndView modelAndView = new ModelAndView("game/lobby");
    Game newGame = gameService.createNewGame();
    modelAndView.addObject("gameId", newGame.getId());
    gameService.addToQueue(newGame);

    return modelAndView;
  }

  @RequestMapping("/new")
  public ModelAndView newGame() {

    ModelAndView modelAndView = new ModelAndView("game/game");
    Game newGame = gameService.createNewGame();
    modelAndView.addObject("gameId", newGame.getId());
    modelAndView.addObject("result", newGame.getGameState().toString());
    modelAndView.addObject("isGameOver", false);
    modelAndView.addObject("wordNum", newGame.getWordNum());
    return modelAndView;
  }

  @RequestMapping("/guess")
  public ModelAndView guessLetter(@Valid SubmitForm submitForm, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView("game/game");
    Game game = gameService.guessLetter(submitForm.getGameId(), submitForm.getLetter());
    if (game.getAttemptsLeft() < 0) {
      game = gameService.getGameState(submitForm.getGameId());
    }

    if (game == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }

    GameStatus gameState = game.getGameState();

    boolean isGameOver =
        gameState == GameStatus.Lost || gameState == GameStatus.Won || game.getAttemptsLeft() <= 0;
    if (isGameOver) {
      return new ModelAndView("user/username_form").addObject("gameId", submitForm.getGameId());
    }

    modelAndView.addObject("gameId", game.getId());
    modelAndView.addObject("result", gameState.toString());
    modelAndView.addObject("lettersUsed", gameService.getUsersLetters(submitForm.getGameId()));
    modelAndView.addObject("wordNum", game.getWordNum());

    modelAndView.addObject("isGameOver", isGameOver);

    return modelAndView;
  }

  @RequestMapping("/reset")
  public ModelAndView resetGame(@Valid SubmitForm submitForm, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView("game/game");

    if (result.hasErrors()) {
      return new ModelAndView("error").addObject("errorMessage",
          result.getFieldError().getDefaultMessage());
    }
    long gameId = submitForm.getGameId();
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }

    String gameInfo = gameService.resetGame(gameId).getGuessedWord().toString();
    modelAndView.addObject("result", gameInfo);
    modelAndView.addObject("gameId", gameId);
    modelAndView.addObject("lettersUsed", gameService.getUsersLetters(gameId));
    modelAndView.addObject("wordNum", game.getWordNum());
    modelAndView.addObject("isGameOver", false);

    return modelAndView;
  }

  @RequestMapping("/getGame")
  public ModelAndView getGameById(@Valid SubmitForm submitForm, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView("game/game");

    if (result.hasErrors()) {
      return new ModelAndView("error").addObject("errorMessage",
          result.getFieldError().getDefaultMessage());
    }
    long gameId = submitForm.getGameId();
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }
    GameStatus gameState = game.getGameState();

    boolean isGameOver =
        gameState == GameStatus.Lost || gameState == GameStatus.Won || game.getAttemptsLeft() <= 0;
    if (isGameOver) {
      return new ModelAndView("user/username_form").addObject("gameId", submitForm.getGameId());
    }
    if (isGameOver) {
      return new ModelAndView("user/username_form").addObject("gameId", gameId);
    }

    modelAndView.addObject("result", gameState.toString());
    modelAndView.addObject("gameId", gameId);
    modelAndView.addObject("lettersUsed", gameService.getUsersLetters(gameId));
    modelAndView.addObject("wordNum", game.getWordNum());
    modelAndView.addObject("isGameOver", isGameOver);

    return modelAndView;
  }

}
