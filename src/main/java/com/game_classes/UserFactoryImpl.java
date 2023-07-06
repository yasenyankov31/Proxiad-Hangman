package com.game_classes;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.game_classes.interfaces.UserFactory;
import com.game_classes.models.UserData;
import com.game_classes.models.dto.UserDto;

@Component
public class UserFactoryImpl implements UserFactory {

  @Override
  public UserDto fromEntity(UserData user) {
    return new UserDto(user);
  }

  @Override
  public List<UserDto> fromEntities(List<UserData> users) {
    List<UserDto> userDtos = new ArrayList<UserDto>();
    for (UserData game : users) {
      userDtos.add(new UserDto(game));
    }
    return userDtos;
  }
}
