package com.game_classes.interfaces.modelInterfaces;

import java.util.Date;

public interface UserRankData {
	String getGameStatus();

	String getWord();

	String getLettersUsed();

	int getAttemptsLeft();

	Date getStartDate();
}
