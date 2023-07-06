package com.game_classes.interfaces;

import java.util.List;
import com.game_classes.models.dto.GameDto;
import com.game_classes.models.game.Game;

public interface GameFactory {
	public GameDto fromEntity(Game game);

	public List<GameDto> fromEntities(List<Game> games);
}
