package com.game_classes.interfaces.ModelInterfaces;

import java.sql.Date;

public interface TopPlayerStats {
	String getUsername();

	int getWinCount();

	int getLostCount();

	Date getFinishDate();
}
