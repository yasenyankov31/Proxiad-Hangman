package com.game_classes.models.dto;

import java.util.List;

public class UserProfileDto {
	List<Integer> statusValues;

	int winCount = 0;
	int lossCount = 0;

	public UserProfileDto() {
	}

	public UserProfileDto(List<Integer> statusValues, int winCount, int lossCount) {
		this.statusValues = statusValues;
		this.winCount = winCount;
		this.lossCount = lossCount;
	}

	public List<Integer> getStatusValues() {
		return statusValues;
	}

	public void setStatusValues(List<Integer> statusValues) {
		this.statusValues = statusValues;
	}

	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public int getLossCount() {
		return lossCount;
	}

	public void setLossCount(int lossCount) {
		this.lossCount = lossCount;
	}
}
