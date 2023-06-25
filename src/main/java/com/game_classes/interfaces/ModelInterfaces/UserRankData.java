package com.game_classes.interfaces.ModelInterfaces;

import java.util.Date;

public interface UserRankData {
	String getGameStatus();

	String getWord();

	String getLettersUsed();

	int getAttemptsLeft();

	Date getStartDate();
}
