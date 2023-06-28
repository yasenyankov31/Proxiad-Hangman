package com.yasen;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.game_classes.interfaces.UserService;
import com.game_classes.models.UserData;

@Controller
public class UserController {
	@Autowired
	private UserService UserService;

	@RequestMapping("/users")
	public ModelAndView getUsersData(Integer pageNum) {
		ModelAndView modelAndView = new ModelAndView("users");
		if (pageNum == null) {
			pageNum = 0;
		}
		Page<UserData> users = UserService.listAllUsers(pageNum);
		modelAndView.addObject("usersData", users);
		modelAndView.addObject("pageNum", pageNum);
		return modelAndView;
	}

	@PostMapping("/updateUser")
	public ModelAndView createUser(@Valid UserData userData, Integer pageNum, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("error").addObject("errorMessage", result.getFieldError().getDefaultMessage());
		}
		UserService.createOrUpdateUser(userData);
		ModelAndView modelAndView = new ModelAndView("users");
		Page<UserData> users = UserService.listAllUsers(pageNum);
		modelAndView.addObject("usersData", users);
		modelAndView.addObject("pageNum", pageNum);
		return modelAndView;
	}

	@PostMapping("/deleteUsers")
	public ModelAndView deleteUsers(@RequestParam("ids") ArrayList<Long> ids, Integer pageNum) {
		if (ids.isEmpty()) {
			return new ModelAndView("error").addObject("errorMessage", "User ids can't be size 0");
		}
		UserService.deleteUsers(ids);
		ModelAndView modelAndView = new ModelAndView("users");
		Page<UserData> users = UserService.listAllUsers(pageNum);
		modelAndView.addObject("usersData", users);
		modelAndView.addObject("pageNum", pageNum);
		return modelAndView;
	}
}
