package com.yasen.soapContractLast.soap_models;

import java.util.List;

public class UserProfileDtoCL {
	List<Integer> statusValues;
	List<UserRankDataDtoCL> userRankDatas;

	int winCount = 0;
	int lossCount = 0;

	public UserProfileDtoCL() {
	}

	public UserProfileDtoCL(List<Integer> statusValues, List<UserRankDataDtoCL> userRankDatas, int winCount,
			int lossCount) {
		this.statusValues = statusValues;
		this.userRankDatas = userRankDatas;
		this.winCount = winCount;
		this.lossCount = lossCount;
	}

	public List<Integer> getStatusValues() {
		return statusValues;
	}

	public void setStatusValues(List<Integer> statusValues) {
		this.statusValues = statusValues;
	}

	public List<UserRankDataDtoCL> getUserRankDatas() {
		return userRankDatas;
	}

	public void setUserRankDatas(List<UserRankDataDtoCL> userRankDatas) {
		this.userRankDatas = userRankDatas;
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
