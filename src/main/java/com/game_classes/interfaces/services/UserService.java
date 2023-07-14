package com.game_classes.interfaces.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.game_classes.models.UserData;

public interface UserService {
	void createUser(UserData user);

	void updateUser(UserData user);

	void deleteUsers(List<Long> ids);

	Page<UserData> listAllUsers(Integer pageNum);

	boolean checkIfUserExist(String username);

	boolean checkIfUserExist(Long id);

	void deleteUser(Long id);

}
