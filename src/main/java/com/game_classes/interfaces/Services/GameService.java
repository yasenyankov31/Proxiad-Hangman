package com.game_classes.interfaces.Services;

import org.springframework.data.domain.Page;
import com.game_classes.models.Game.Game;

public interface GameService {
  Game createNewGame();

  Game guessLetter(long gameId, char letter);

  Game getGameState(long gameId);

  Game resetGame(long gameId);

  Page<Game> getUnfinishedGames(int pageNum);

  String getRandomWord();

  String getUsersLetters(long gameId);

  void addOponentId(long id, Game userGame);

  void addToQueue(Game game);

  long getFromQueue();
}
