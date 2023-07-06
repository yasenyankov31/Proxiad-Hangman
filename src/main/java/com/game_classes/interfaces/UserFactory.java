package com.game_classes.interfaces;

import java.util.List;
import com.game_classes.models.UserData;
import com.game_classes.models.dto.UserDto;

public interface UserFactory {
  public UserDto fromEntity(UserData user);

  public List<UserDto> fromEntities(List<UserData> users);
}
