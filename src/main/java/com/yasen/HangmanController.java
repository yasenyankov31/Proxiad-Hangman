package com.yasen;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.game_classes.interfaces.GameService;
import com.game_classes.models.Game;
import com.game_classes.models.SubmitForm;

@Controller
public class HangmanController {

  @Autowired private GameService gameService;

  @RequestMapping("/")
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  @RequestMapping("/createParty")
  public ModelAndView createParty() {
    ModelAndView modelAndView = new ModelAndView("lobby");
    Game newGame = gameService.createNewGame();
    modelAndView.addObject("gameId", newGame.getId());

    return modelAndView;
  }

  @RequestMapping("/new")
  public ModelAndView newGame() {
    ModelAndView modelAndView = new ModelAndView("game");
    Game newGame = gameService.createNewGame();
    modelAndView.addObject("gameId", newGame.getId());
    modelAndView.addObject("result", newGame.getInfo());
    modelAndView.addObject("isGameOver", false);
    modelAndView.addObject("wordNum", newGame.getWordNum());
    return modelAndView;
  }

  @RequestMapping("/guess")
  public ModelAndView guessLetter(@Valid SubmitForm submitForm, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView("game");

    if (result.hasErrors()) {
      return new ModelAndView("error")
          .addObject("errorMessage", result.getFieldError().getDefaultMessage());
    }
    Game game = gameService.guessLetter(submitForm.getGameId(), submitForm.getLetter());

    if (game == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }

    String gameInfo = game.getInfo();
    boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won");

    modelAndView.addObject("gameId", game.getId());
    modelAndView.addObject("result", gameInfo);
    modelAndView.addObject("lettersUsed", gameService.getUsersLetters(submitForm.getGameId()));
    modelAndView.addObject("wordNum", game.getWordNum());
    modelAndView.addObject("isGameOver", isGameOver);
    if (game.getOpponentId() != null) {
      modelAndView.addObject("opponentId", game.getOpponentId());
      modelAndView.addObject(
          "opponentInfo", gameService.getGameState(game.getOpponentId()).getInfo());
    }

    return modelAndView;
  }

  @RequestMapping("/reset")
  public ModelAndView resetGame(@Valid SubmitForm submitForm, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView("game");

    if (result.hasErrors()) {
      return new ModelAndView("error")
          .addObject("errorMessage", result.getFieldError().getDefaultMessage());
    }
    String gameId = submitForm.getGameId();
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
    ModelAndView modelAndView = new ModelAndView("game");

    if (result.hasErrors()) {
      return new ModelAndView("error")
          .addObject("errorMessage", result.getFieldError().getDefaultMessage());
    }
    String gameId = submitForm.getGameId();
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }

    String gameInfo = game.getInfo();
    boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won");

    modelAndView.addObject("result", gameInfo);
    modelAndView.addObject("gameId", gameId);
    modelAndView.addObject("lettersUsed", gameService.getUsersLetters(gameId));
    modelAndView.addObject("wordNum", game.getWordNum());
    modelAndView.addObject("isGameOver", isGameOver);

    return modelAndView;
  }

  @RequestMapping("/getOpponentInfo")
  public void getOpponentInfo(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String gameid = request.getParameter("opponentId");
    response.getWriter().print(gameService.getGameState(gameid).getInfo());
  }

  @RequestMapping("/startMasterGame")
  public ModelAndView startMasterGame(String gameId) {
    ModelAndView modelAndView = new ModelAndView("game");
    Game masterGame = gameService.getGameState(gameId);
    modelAndView.addObject("gameId", masterGame.getId());
    modelAndView.addObject("result", masterGame.getInfo());
    modelAndView.addObject("isGameOver", false);
    modelAndView.addObject("wordNum", masterGame.getWordNum());
    modelAndView.addObject("opponentId", masterGame.getOpponentId());
    Game opponentGame = gameService.getGameState(masterGame.getOpponentId());
    if (opponentGame == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }

    modelAndView.addObject("opponentInfo", opponentGame.getInfo());
    return modelAndView;
  }

  @RequestMapping("/checkIfGameStarted")
  public void checkIfGameStarted(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String gameid = request.getParameter("gameId");
    response.getWriter().print(gameService.getGameState(gameid).getOpponentId());
  }

  @RequestMapping("/joinPartyGameById")
  public ModelAndView joinPartyGameById(@Valid SubmitForm submitForm, BindingResult result) {
    if (result.hasErrors()) {
      return new ModelAndView("error")
          .addObject("errorMessage", result.getFieldError().getDefaultMessage());
    }
    ModelAndView modelAndView = new ModelAndView("game");
    Game newGame = gameService.createNewGame();
    Game masterGame = gameService.getGameState(submitForm.getGameId());
    if (masterGame == null) {
      return new ModelAndView("error").addObject("errorMessage", "Game not found!");
    }
    gameService.addOponentId(masterGame.getId(), newGame);
    gameService.addOponentId(newGame.getId(), masterGame);

    modelAndView.addObject("master", false);
    modelAndView.addObject("opponentId", masterGame.getId());
    modelAndView.addObject("gameId", newGame.getId());
    modelAndView.addObject("result", newGame.getInfo());
    modelAndView.addObject("isGameOver", false);
    modelAndView.addObject("wordNum", newGame.getWordNum());
    modelAndView.addObject("opponentInfo", gameService.getGameState(masterGame.getId()).getInfo());

    return modelAndView;
  }
}
