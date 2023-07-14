package com.game_classes.interfaces;

import org.springframework.data.domain.Page;

import com.game_classes.models.UserData;
import com.game_classes.models.dto.UserDto;

public interface UserFactory {
	public UserDto fromEntity(UserData user);

	public Page<UserDto> fromEntities(Page<UserData> users);
}
