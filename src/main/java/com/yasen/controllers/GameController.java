package com.yasen.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.game_classes.interfaces.Services.GameService;
import com.game_classes.interfaces.Services.RankingService;
import com.game_classes.models.SubmitForm;
import com.game_classes.models.Game.Game;

@Controller
public class GameController {

  @Autowired private GameService gameService;

  @Autowired private RankingService rankingService;

  @RequestMapping("/")
  public ModelAndView index() {

    List<String> userAllTime = new ArrayList<>();
    List<Integer> winAllTime = new ArrayList<>();

    List<String> userForMonth = new ArrayList<>();
    List<Integer> winsForMonth = new ArrayList<>();

    rankingService
        .topTenOfAllTime()
        .forEach(
            item -> {
              userAllTime.add('"' + item.getUsername() + '"');
              winAllTime.add(item.getWinCount());
            });
    rankingService
        .topTenOfTheMonth()
        .forEach(
            item -> {
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
  public ModelAndView endingResult(String username, long gameId) {
    if (gameId == 0) {
      return new ModelAndView("error").addObject("errorMessage", "Not existing game!");
    }
    if (username.isBlank() || username.isEmpty()) {
      return new ModelAndView("error")
          .addObject("errorMessage", "Write at least one character for UserDataname! ");
    }
    Game game = gameService.getGameState(gameId);
    ModelAndView modelAndView = new ModelAndView("game/game");
    String gameInfo = game.getInfo();

    boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won");
    rankingService.completeGame(game, username);
    modelAndView.addObject("gameId", game.getId());
    modelAndView.addObject("result", gameInfo);
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
    modelAndView.addObject("result", newGame.getInfo());
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

    String gameInfo = game.getInfo();
    boolean isGameOver =
        gameInfo.contains("Game over")
            || gameInfo.contains("Game won")
            || game.getAttemptsLeft() <= 0;
    if (isGameOver) {
      return new ModelAndView("user/username_form").addObject("gameId", submitForm.getGameId());
    }

    modelAndView.addObject("gameId", game.getId());
    modelAndView.addObject("result", gameInfo);
    modelAndView.addObject("lettersUsed", gameService.getUsersLetters(submitForm.getGameId()));
    modelAndView.addObject("wordNum", game.getWordNum());

    modelAndView.addObject("isGameOver", isGameOver);

    if (game.getOpponentId() != 0) {
      modelAndView.addObject("opponentId", game.getOpponentId());
      modelAndView.addObject(
          "opponentInfo", gameService.getGameState(game.getOpponentId()).getInfo());
    }

    return modelAndView;
  }

  @RequestMapping("/reset")
  public ModelAndView resetGame(@Valid SubmitForm submitForm, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView("game/game");

    if (result.hasErrors()) {
      return new ModelAndView("error")
          .addObject("errorMessage", result.getFieldError().getDefaultMessage());
    }
    long gameId = submitForm.getGameId();
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }

    String gameInfo = gameService.resetGame(gameId).getInfo();
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
      return new ModelAndView("error")
          .addObject("errorMessage", result.getFieldError().getDefaultMessage());
    }
    long gameId = submitForm.getGameId();
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }

    String gameInfo = game.getInfo();
    boolean isGameOver =
        gameInfo.contains("Game over")
            || gameInfo.contains("Game won")
            || game.getAttemptsLeft() <= 0;

    if (isGameOver) {
      return new ModelAndView("user/username_form").addObject("gameId", gameId);
    }

    modelAndView.addObject("result", gameInfo);
    modelAndView.addObject("gameId", gameId);
    modelAndView.addObject("lettersUsed", gameService.getUsersLetters(gameId));
    modelAndView.addObject("wordNum", game.getWordNum());
    modelAndView.addObject("isGameOver", isGameOver);

    return modelAndView;
  }

  // @RequestMapping("/getOpponentInfo")
  // public void getOpponentInfo(HttpServletRequest request, HttpServletResponse
  // response)
  // throws IOException {
  // long gameid = Long.parseLong(request.getParameter("opponentId"));
  // response.getWriter().print(gameService.getGameState(gameid).getInfo());
  // }
  //
  // @RequestMapping("/startMasterGame")
  // public ModelAndView startMasterGame(long gameId) {
  // ModelAndView modelAndView = new ModelAndView("game");
  // Game masterGame = gameService.getGameState(gameId);
  // modelAndView.addObject("gameId", masterGame.getId());
  // modelAndView.addObject("result", masterGame.getInfo());
  // modelAndView.addObject("isGameOver", false);
  // modelAndView.addObject("wordNum", masterGame.getWordNum());
  // modelAndView.addObject("opponentId", masterGame.getOpponentId());
  // Game opponentGame = gameService.getGameState(masterGame.getOpponentId());
  //
  // if (opponentGame == null) {
  // return new ModelAndView("error").addObject("errorMessage", "Game not
  // found!");
  // }
  //
  // modelAndView.addObject("opponentInfo", opponentGame.getInfo());
  // return modelAndView;
  // }
  //
  // @RequestMapping("/checkIfGameStarted")
  // public void checkIfGameStarted(HttpServletRequest request,
  // HttpServletResponse response)
  // throws IOException {
  // long gameid = Long.parseLong(request.getParameter("gameId"));
  // response.getWriter().print(gameService.getGameState(gameid).getOpponentId());
  // }

  // @RequestMapping("/joinRandomPartyGame")
  // public ModelAndView joinRandomPartyGame(String UserDataname) {
  // ModelAndView modelAndView = new ModelAndView("game");
  // long opponentId = gameService.getFromQueue();
  // if (opponentId == 0) {
  // return new ModelAndView("error").addObject("errorMessage", "No games in
  // queue!");
  // }
  // Game newGame = gameService.createNewGame(UserDataname);
  // Game masterGame = gameService.getGameState(opponentId);
  // if (masterGame == null) {
  // return new ModelAndView("error").addObject("errorMessage", "Game not
  // found!");
  // }
  // gameService.addOponentId(masterGame.getId(), newGame);
  // gameService.addOponentId(newGame.getId(), masterGame);
  //
  // modelAndView.addObject("master", false);
  // modelAndView.addObject("opponentId", masterGame.getId());
  // modelAndView.addObject("gameId", newGame.getId());
  // modelAndView.addObject("result", newGame.getInfo());
  // modelAndView.addObject("isGameOver", false);
  // modelAndView.addObject("wordNum", newGame.getWordNum());
  // modelAndView.addObject("opponentInfo",
  // gameService.getGameState(masterGame.getId()).getInfo());
  //
  // return modelAndView;
  // }
  //
  // @RequestMapping("/joinPartyGameById")
  // public ModelAndView joinPartyGameById(
  // @Valid SubmitForm submitForm, String UserDataname, BindingResult result) {
  // if (result.hasErrors()) {
  // return new ModelAndView("error")
  // .addObject("errorMessage", result.getFieldError().getDefaultMessage());
  // }
  // ModelAndView modelAndView = new ModelAndView("game");
  // Game newGame = gameService.createNewGame(UserDataname);
  // Game masterGame = gameService.getGameState(submitForm.getGameId());
  // if (masterGame == null) {
  // return new ModelAndView("error").addObject("errorMessage", "Game not
  // found!");
  // }
  // gameService.addOponentId(masterGame.getId(), newGame);
  // gameService.addOponentId(newGame.getId(), masterGame);
  //
  // modelAndView.addObject("master", false);
  // modelAndView.addObject("opponentId", masterGame.getId());
  // modelAndView.addObject("gameId", newGame.getId());
  // modelAndView.addObject("result", newGame.getInfo());
  // modelAndView.addObject("isGameOver", false);
  // modelAndView.addObject("wordNum", newGame.getWordNum());
  // modelAndView.addObject("opponentInfo",
  // gameService.getGameState(masterGame.getId()).getInfo());
  //
  // return modelAndView;
  // }
}
