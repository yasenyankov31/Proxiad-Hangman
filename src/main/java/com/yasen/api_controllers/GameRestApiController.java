package com.yasen.api_controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.game_classes.interfaces.GameFactory;
import com.game_classes.interfaces.services.GameService;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.models.ErrorResponse;
import com.game_classes.models.dto.GameDto;
import com.game_classes.models.game.Game;
import com.game_classes.models.game.RankingData;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/games")
public class GameRestApiController {
  @Autowired
  private GameService gameService;

  @Autowired
  private RankingService rankingService;

  @Autowired
  private GameFactory gameFactory;

  @GetMapping
  @Operation(summary = "Show statistics for all the users that have played",
      description = "Returns a montly and all time ranking data lists.")
  public Map<String, RankingData> getRankingData() {
    RankingData allTimeData = new RankingData();
    RankingData monthData = new RankingData();

    rankingService.topTenOfAllTime().forEach(item -> {
      allTimeData.getUserNames().add(item.getUsername());
      allTimeData.getWinCounts().add(item.getWinCount());
    });

    rankingService.topTenOfTheMonth().forEach(item -> {
      monthData.getUserNames().add(item.getUsername());
      monthData.getWinCounts().add(item.getWinCount());
    });

    Map<String, RankingData> responseData = new HashMap<>();
    responseData.put("allTime", allTimeData);
    responseData.put("month", monthData);

    return responseData;
  }

  @GetMapping("/not-completed-games")
  @Operation(summary = "Shows all not completed games", description = "Returns a list of games.")
  public ResponseEntity<Page<GameDto>> getNotCompletedGames(
      @RequestParam(required = false) Integer pageNum) {
    if (pageNum == null) {
      pageNum = 0;
    } else {
      pageNum -= 1;
    }

    Page<Game> unfinishedGames = gameService.getUnfinishedGames(pageNum);
    if (pageNum > unfinishedGames.getTotalPages()) {
      throw new IllegalArgumentException("Page doesn't exist!");
    }

    return ResponseEntity.ok().body(gameFactory.fromEntities(unfinishedGames));
  }

  @PostMapping
  @Operation(summary = "Creates new game", description = "Returns a new game.")
  public ResponseEntity<GameDto> createNewGame() {
    Game newGame = gameService.createNewGame();
    return ResponseEntity.ok().body(gameFactory.fromEntity(newGame));
  }

  @PutMapping("/{gameId}/guess-letter")
  @Operation(summary = "Checks if the sented letter is in the game word",
      description = "Returns an updated game.")
  public ResponseEntity<GameDto> guessLetter(@PathVariable Long gameId,
      @RequestParam Character letter) {
    Game game = gameService.getGameState(gameId);
    GameDto gameDto = null;
    if (game == null) {
      throw new IllegalArgumentException("Game doesn't exist!");
    }

    if (game.getAttemptsLeft() > 0) {
      game = gameService.guessLetter(gameId, letter);
      gameDto = gameFactory.fromEntity(game);
      gameDto.setLettersUsed(gameService.getUsersLetters(gameId));
      gameDto.setWordNum(game.getWordNum());
      gameDto.setCompletedGame(gameService.checkIfGameIsCompleted(gameId));
      if (game.getAttemptsLeft() == 0 || !game.getGuessedWord().contains("*")) {
        rankingService.completeGame(game, game.getUserGame().getUsername());
        gameDto = gameFactory.fromEntity(game);
        gameDto.setOriginalWord(game.getWord());
      }
    }

    return ResponseEntity.ok().body(gameDto);
  }

  @PutMapping("/{gameId}/reset")
  @Operation(summary = "Refreshes the word and the attempts for an ongoing game ",
      description = "Returns a new game.")
  public ResponseEntity<GameDto> resetGame(@PathVariable Long gameId) {
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      throw new IllegalArgumentException("Game doesn't exist!");
    }
    if (gameService.checkIfGameIsCompleted(gameId)) {
      throw new IllegalArgumentException("Game already completed!");
    }

    gameService.resetGame(gameId);
    game.setLettersUsed(gameService.getUsersLetters(gameId));
    game.setWordNum(game.getWordNum());

    return ResponseEntity.ok().body(gameFactory.fromEntity(game));
  }

  @GetMapping("/{gameId}")
  @Operation(summary = "Shows specific game by id", description = "Returns an ongoing game.")
  public ResponseEntity<GameDto> getGameById(@PathVariable Long gameId) {
    Game game = gameService.getGameState(gameId);

    if (game == null) {
      throw new IllegalArgumentException("Game doesn't exist!");
    }

    game.setLettersUsed(gameService.getUsersLetters(gameId));
    game.setWordNum(game.getWordNum());
    GameDto gameDto = gameFactory.fromEntity(game);
    boolean isCompletedGame = gameService.checkIfGameIsCompleted(gameId);
    gameDto.setCompletedGame(isCompletedGame);
    if (isCompletedGame) {
      gameDto.setOriginalWord(game.getWord());
    }

    return ResponseEntity.ok().body(gameDto);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Bad request", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }


}
