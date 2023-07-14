package com.game_classes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.game_classes.interfaces.GameFactory;
import com.game_classes.models.dto.GameDto;
import com.game_classes.models.game.Game;

@Component
public class GameFactoryImpl implements GameFactory {

	@Override
	public GameDto fromEntity(Game game) {
		GameDto gameDto = new GameDto(game);
		return gameDto;
	}

	@Override
	public Page<GameDto> fromEntities(Page<Game> games) {
		List<GameDto> gameDtos = new ArrayList<>();
		for (Game gameData : games.getContent()) {
			gameDtos.add(new GameDto(gameData));
		}
		return new PageImpl<>(gameDtos, games.getPageable(), games.getTotalElements());
	}

}
