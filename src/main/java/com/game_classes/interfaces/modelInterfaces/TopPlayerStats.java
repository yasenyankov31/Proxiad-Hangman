package com.game_classes.interfaces.modelInterfaces;

import java.sql.Date;

public interface TopPlayerStats {
	String getUsername();

	int getWinCount();

	int getLostCount();

	Date getFinishDate();
}
