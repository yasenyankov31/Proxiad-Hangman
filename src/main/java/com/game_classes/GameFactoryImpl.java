package com.game_classes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.game_classes.interfaces.GameFactory;
import com.game_classes.models.Dto.GameDto;
import com.game_classes.models.Game.Game;

@Component
public class GameFactoryImpl implements GameFactory {

	@Override
	public GameDto fromEntity(Game game) {
		GameDto gameDto = new GameDto(game);
		return gameDto;
	}

	@Override
	public List<GameDto> fromEntities(List<Game> games) {
		List<GameDto> gamesDtos = new ArrayList<GameDto>();
		for (Game game : games) {
			gamesDtos.add(new GameDto(game));
		}
		return gamesDtos;
	}

}
