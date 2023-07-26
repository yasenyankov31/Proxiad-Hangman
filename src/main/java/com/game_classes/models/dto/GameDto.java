package com.game_classes.models.dto;

import java.util.Date;

import com.game_classes.models.GameStatus;
import com.game_classes.models.game.Game;

public class GameDto {
	private Long id;
	private String guessedWord;
	private String lettersUsed;
	private int attemptsLeft;
	private int wordNum;
	private Date date;
	private GameStatus gameStatus;
	private boolean isCompletedGame;
	private String originalWord;

	public GameDto() {
	}

	public GameDto(Game game) {
		this.id = game.getId();
		this.guessedWord = game.getGuessedWord();
		this.lettersUsed = game.getLetters();
		this.attemptsLeft = game.getAttemptsLeft();
		this.wordNum = game.getWordNum();
		this.date = game.getDate();
		this.gameStatus = game.getGameState();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGuessedWord() {
		return guessedWord;
	}

	public void setGuessedWord(String guessedWord) {
		this.guessedWord = guessedWord;
	}

	public String getLettersUsed() {
		return lettersUsed;
	}

	public void setLettersUsed(String lettersUsed) {
		this.lettersUsed = lettersUsed;
	}

	public int getAttemptsLeft() {
		return attemptsLeft;
	}

	public void setAttemptsLeft(int attemptsLeft) {
		this.attemptsLeft = attemptsLeft;
	}

	public int getWordNum() {
		return wordNum;
	}

	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public boolean isCompletedGame() {
		return isCompletedGame;
	}

	public void setCompletedGame(boolean isCompletedGame) {
		this.isCompletedGame = isCompletedGame;
	}

	public String getOriginalWord() {
		return originalWord;
	}

	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;
	}

}
