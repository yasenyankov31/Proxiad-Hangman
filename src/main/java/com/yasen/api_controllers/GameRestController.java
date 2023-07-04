package com.yasen.api_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.game_classes.interfaces.Services.GameService;
import com.game_classes.interfaces.Services.RankingService;
import com.game_classes.models.SubmitForm;
import com.game_classes.models.Game.Game;
import com.game_classes.models.Game.RankingData;

@RestController
@RequestMapping("/api/ranking")
public class GameRestController {
  @Autowired private GameService gameService;

  @Autowired private RankingService rankingService;

  @GetMapping("/")
  public Map<String, RankingData> getRankingData() {
    List<String> userAllTime = new ArrayList<>();
    List<Integer> winAllTime = new ArrayList<>();

    List<String> userForMonth = new ArrayList<>();
    List<Integer> winsForMonth = new ArrayList<>();

    rankingService
        .topTenOfAllTime()
        .forEach(
            item -> {
              userAllTime.add(item.getUsername());
              winAllTime.add(item.getWinCount());
            });

    rankingService
        .topTenOfTheMonth()
        .forEach(
            item -> {
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

  @GetMapping("/notCompletedGames")
  public ResponseEntity<List<Game>> getNotCompletedGames(
      @RequestParam(required = false) Integer pageNum) {
    if (pageNum == null) {
      pageNum = 0;
    }
    Page<Game> unfinishedGames = gameService.getUnfinishedGames(pageNum);
    return ResponseEntity.ok().body(unfinishedGames.getContent());
  }

  @GetMapping("/endingResult")
  public ResponseEntity<String> getEndingResult(
      @RequestParam String username, @RequestParam long gameId) {
    if (gameId == 0) {
      return ResponseEntity.badRequest().body("Not existing game!");
    }
    if (username.isBlank() || username.isEmpty()) {
      return ResponseEntity.badRequest().body("Write at least one character for Username!");
    }
    Game game = gameService.getGameState(gameId);
    String gameInfo = game.getInfo();
    boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won");
    rankingService.completeGame(game, username);
    return ResponseEntity.ok().body(gameInfo);
  }

  @PostMapping("/new")
  public ResponseEntity<Game> createNewGame() {
    Game newGame = gameService.createNewGame();
    return ResponseEntity.ok().body(newGame);
  }

  @PostMapping("/guess")
  public ResponseEntity<Game> guessLetter(
      @Valid @RequestBody SubmitForm submitForm, BindingResult result) {
    Game game = gameService.guessLetter(submitForm.getGameId(), submitForm.getLetter());
    if (game.getAttemptsLeft() < 0) {
      game = gameService.getGameState(submitForm.getGameId());
    }

    if (game == null) {
      return ResponseEntity.badRequest().body(null);
    }

    String gameInfo = game.getInfo();
    boolean isGameOver = gameInfo.contains("Game over") || gameInfo.contains("Game won");

    if (isGameOver) {
      return ResponseEntity.ok().body(game);
    }

    game.setLettersUsed(gameService.getUsersLetters(submitForm.getGameId()));
    game.setWordNum(game.getWordNum());

    return ResponseEntity.ok().body(game);
  }

  @PostMapping("/reset")
  public ResponseEntity<Game> resetGame(
      @Valid @RequestBody SubmitForm submitForm, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(null);
    }
    long gameId = submitForm.getGameId();
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      return ResponseEntity.badRequest().body(null);
    }

    gameService.resetGame(gameId);
    game.setLettersUsed(gameService.getUsersLetters(gameId));
    game.setWordNum(game.getWordNum());

    return ResponseEntity.ok().body(game);
  }

  @PostMapping("/getGame/{gameId}")
  public ResponseEntity<Game> getGameById(@PathVariable long gameId) {
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      return ResponseEntity.badRequest().body(null);
    }

    String gameInfo = game.getInfo();
    boolean isGameOver =
        gameInfo.contains("Game over")
            || gameInfo.contains("Game won")
            || game.getAttemptsLeft() <= 0;

    if (isGameOver) {
      return ResponseEntity.ok().body(game);
    }

    game.setLettersUsed(gameService.getUsersLetters(gameId));
    game.setWordNum(game.getWordNum());

    return ResponseEntity.ok().body(game);
  }
}