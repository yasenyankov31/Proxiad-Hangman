package com.game_classes.models.game;

import java.util.ArrayList;
import java.util.List;

public class RankingData {
	private List<String> userNames = new ArrayList<String>();
	private List<Integer> winCounts = new ArrayList<Integer>();

	public List<String> getUserNames() {
		return userNames;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}

	public List<Integer> getWinCounts() {
		return winCounts;
	}

	public void setWinCounts(List<Integer> winCounts) {
		this.winCounts = winCounts;
	}
}
