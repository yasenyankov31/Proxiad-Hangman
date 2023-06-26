package com.game_classes.interfaces;

import java.util.List;
import com.game_classes.models.UserData;

public interface UserService {
  void createOrUpdateUser(UserData user);

  void deleteUser(long userId);

  List<UserData> listAllUsers();
}
