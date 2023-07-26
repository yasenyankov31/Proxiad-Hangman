package com.game_classes.interfaces.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.game_classes.models.game.Game;

public interface GameService {
	Game createNewGame();

	Game guessLetter(long gameId, char letter);

	Game getGameState(long gameId);

	Game resetGame(long gameId);

	Page<Game> getUnfinishedGames(int pageNum);

	List<Game> getUnfinishedGames();

	boolean checkIfGameIsCompleted(long gameId);

	String getRandomWord();

	String getUsersLetters(long gameId);

	void addOponentId(long id, Game userGame);

	void addToQueue(Game game);

	long getFromQueue();
}
