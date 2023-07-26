package com.game_classes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.game_classes.interfaces.jpaRepositories.UserRepository;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.models.UserData;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public void createUser(UserData user) {
    userRepository.save(user);
  }

  @Override
  public void updateUser(UserData user) {
    userRepository.save(user);
  }

  @Override
  public Page<UserData> listAllUsers(Integer pageNum) {
    PageRequest pageable = PageRequest.of(pageNum, 5, Sort.by(Sort.Direction.DESC, "id"));
    return userRepository.findAll(pageable);
  }

  @Override
  public void deleteUsers(List<Long> ids) {
    userRepository.deleteAllById(ids);
  }

  @Override
  public boolean checkIfUserExist(String username) {
    return userRepository.findAllByUsername(username).size() > 0;
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);

  }

  @Override
  public boolean checkIfUserExist(Long id) {
    return userRepository.findById(id).isPresent();
  }
}
