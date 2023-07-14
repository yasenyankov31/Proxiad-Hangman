package com.game_classes.interfaces;

import org.springframework.data.domain.Page;

import com.game_classes.models.dto.GameDto;
import com.game_classes.models.game.Game;

public interface GameFactory {
	public GameDto fromEntity(Game game);

	public Page<GameDto> fromEntities(Page<Game> games);
}
