package com.game_classes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game_classes.interfaces.UserService;
import com.game_classes.interfaces.JpaRepositories.UserRepository;
import com.game_classes.models.UserData;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private UserRepository userRepository;

  @Override
  public void createOrUpdateUser(UserData user) {
    userRepository.save(user);
  }

  @Override
  public void deleteUser(long userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public List<UserData> listAllUsers() {
    return userRepository.findAll();
  }
}
