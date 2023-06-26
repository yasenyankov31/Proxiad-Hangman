package com.game_classes.interfaces;

import java.util.List;
import com.game_classes.models.Game;

public interface GameService {
  Game createNewGame();

  Game guessLetter(long gameId, char letter);

  Game getGameState(long gameId);

  Game resetGame(long gameId);

  List<Game> getUnfinishedGames();

  String getRandomWord();

  String getUsersLetters(long gameId);

  void addOponentId(long id, Game userGame);

  void addToQueue(Game game);

  long getFromQueue();
}
