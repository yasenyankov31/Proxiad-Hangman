package com.yasen.soapContractLast.soap_models;

import java.util.Date;

import com.game_classes.interfaces.modelInterfaces.UserRankData;

public class UserRankDataDtoCL implements UserRankData {
	String gameStatus;

	String word;

	String lettersUsed;

	int attemptsLeft;

	Date startDate;

	public UserRankDataDtoCL(UserRankData userRankData) {
		this.gameStatus = userRankData.getGameStatus();
		this.word = userRankData.getWord();
		this.lettersUsed = userRankData.getLettersUsed();
		this.attemptsLeft = userRankData.getAttemptsLeft();
		this.startDate = userRankData.getStartDate();
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

}
