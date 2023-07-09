package com.game_classes.models.dto;

import java.util.Date;

import com.game_classes.models.game.Game;

public class GameDto {
	private long id;
	private String guessedWord;
	private String lettersUsed;
	private int attemptsLeft;
	private int wordNum;
	private Date date;
	private boolean isGameOver;

	public GameDto() {
	}

	public GameDto(Game game) {
		this.id = game.getId();
		this.guessedWord = game.getGuessedWord();
		this.lettersUsed = game.getLetters();
		this.attemptsLeft = game.getAttemptsLeft();
		this.wordNum = game.getWordNum();
		this.date = game.getDate();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
}
