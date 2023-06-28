package com.game_classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.game_classes.interfaces.UserService;
import com.game_classes.interfaces.JpaRepositories.UserRepository;
import com.game_classes.models.UserData;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public void createOrUpdateUser(UserData user) {
		userRepository.save(user);
	}

	@Override
	public Page<UserData> listAllUsers(Integer pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return userRepository.findAll(pageable);
	}

	@Override
	public void deleteUsers(List<Long> ids) {
		userRepository.deleteAllById(ids);

	}
}
