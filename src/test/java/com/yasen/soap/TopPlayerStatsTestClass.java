package com.yasen.soap;

import java.sql.Date;

import com.game_classes.interfaces.modelInterfaces.TopPlayerStats;

public class TopPlayerStatsTestClass implements TopPlayerStats {

	String username;
	int winCount;
	int lostCount;
	Date finishDate;

	public TopPlayerStatsTestClass(String username, int winCount, int lostCount, Date finishDate) {
		this.username = username;
		this.winCount = winCount;
		this.lostCount = lostCount;
		this.finishDate = finishDate;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public int getWinCount() {
		// TODO Auto-generated method stub
		return this.winCount;
	}

	@Override
	public int getLostCount() {
		// TODO Auto-generated method stub
		return this.lostCount;
	}

	@Override
	public Date getFinishDate() {
		// TODO Auto-generated method stub
		return this.finishDate;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public void setLostCount(int lostCount) {
		this.lostCount = lostCount;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

}
