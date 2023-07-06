package com.game_classes.interfaces.services;

import java.util.List;
import org.springframework.data.domain.Page;
import com.game_classes.models.UserData;

public interface UserService {
  void createOrUpdateUser(UserData user);

  void deleteUsers(List<Long> ids);

  Page<UserData> listAllUsers(Integer pageNum);
}
