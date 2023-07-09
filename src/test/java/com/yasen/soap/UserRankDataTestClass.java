package com.yasen.soap;

import java.util.Date;

import com.game_classes.interfaces.modelInterfaces.UserRankData;

public class UserRankDataTestClass implements UserRankData {

	String gameStatus;
	String word;
	String lettersUsed;
	int attemptsLeft;
	Date startDate;

	public UserRankDataTestClass(String gameStatus, String word, String lettersUsed, int attemptsLeft, Date startDate) {
		super();
		this.gameStatus = gameStatus;
		this.word = word;
		this.lettersUsed = lettersUsed;
		this.attemptsLeft = attemptsLeft;
		this.startDate = startDate;
	}

	@Override
	public String getGameStatus() {
		// TODO Auto-generated method stub
		return this.gameStatus;
	}

	@Override
	public String getWord() {
		// TODO Auto-generated method stub
		return this.word;
	}

	@Override
	public String getLettersUsed() {
		// TODO Auto-generated method stub
		return this.lettersUsed;
	}

	@Override
	public int getAttemptsLeft() {
		// TODO Auto-generated method stub
		return this.attemptsLeft;
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return this.startDate;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setLettersUsed(String lettersUsed) {
		this.lettersUsed = lettersUsed;
	}

	public void setAttemptsLeft(int attemptsLeft) {
		this.attemptsLeft = attemptsLeft;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
