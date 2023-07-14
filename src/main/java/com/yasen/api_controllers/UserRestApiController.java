package com.yasen.api_controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.game_classes.interfaces.UserFactory;
import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.models.ErrorResponse;
import com.game_classes.models.SuccessResponse;
import com.game_classes.models.UserData;
import com.game_classes.models.dto.UserDto;
import com.game_classes.models.dto.UserProfileDto;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class UserRestApiController {
	@Autowired
	private UserService userService;

	@Autowired
	private RankingService rankingService;

	@Autowired
	private UserFactory userFactory;

	@GetMapping("/{pageNum}")
	@Operation(summary = "Show all users", description = "Returns a list of user data.")
	public ResponseEntity<Page<UserDto>> getUsersData(@PathVariable Integer pageNum) {
		if (pageNum == null) {
			pageNum = 0;
		}
		Page<UserData> users = userService.listAllUsers(pageNum);

		return ResponseEntity.ok().body(userFactory.fromEntities(users));
	}

	@GetMapping("/user/{username}")
	@Operation(summary = "Get user game statistics and played games", description = "Returns  statistic of a user profile.")
	public ResponseEntity<UserProfileDto> userProfile(@PathVariable(required = true) String username,
			@RequestParam(required = false) Integer pageNum) {
		if (!userService.checkIfUserExist(username)) {
			throw new IllegalArgumentException("Username doesn't exist!");
		}

		pageNum = pageNum != null ? pageNum : 0;
		int winCount = 0;
		int lossCount = 0;
		int prevWinCount = 0;
		List<Integer> statusValues = new ArrayList<>();
		Page<UserRankData> userRankDatas = rankingService.getUserInfo(username, pageNum);
		Page<UserRankData> userRankProgressAllTime = rankingService.getUserInfo(username, null);

		for (UserRankData rankData : userRankProgressAllTime) {
			if (rankData.getGameStatus().equals("Won")) {
				winCount++;
				statusValues.add(++prevWinCount);
			} else {
				lossCount++;
				statusValues.add(0);
				prevWinCount = 0;
			}
		}

		UserProfileDto userProfileDto = new UserProfileDto(statusValues, userRankDatas, winCount, lossCount);

		return ResponseEntity.ok(userProfileDto);
	}

	@PostMapping("/user")
	@Operation(summary = "Creates new user", description = "Returns a list of updated user data.")
	public ResponseEntity<SuccessResponse> createUser(@Valid @RequestBody UserData userData, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("User formdata is invalid!");
		}

		userService.createUser(userData);
		SuccessResponse response = new SuccessResponse("createUser", "User created successfully!");

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/user")
	@Operation(summary = "Updates user information", description = "Returns a list of updated user data.")
	public ResponseEntity<SuccessResponse> updateUser(@Valid @RequestBody UserData userData, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("User formdata is invalid!");
		}

		userService.updateUser(userData);

		SuccessResponse response = new SuccessResponse("updateUser", "User updated successfully!");

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/user/{userId}")
	@Operation(summary = "Deletes single user", description = "Returns a list of updated user data.")
	public ResponseEntity<SuccessResponse> deleteUser(@PathVariable(required = true) Long userId) {

		if (!userService.checkIfUserExist(userId)) {
			throw new IllegalArgumentException("User doesn't exist!");
		}
		userService.deleteUser(userId);
		SuccessResponse response = new SuccessResponse("deleteUser", "User deleted successfully!");

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/")
	@Operation(summary = "Deletes list of users", description = "Returns a list of updated user data.")
	public ResponseEntity<SuccessResponse> deleteUsers(@RequestBody List<Long> ids) {
		if (ids.isEmpty()) {
			throw new IllegalArgumentException("Empty list of ids!");
		}

		userService.deleteUsers(ids);
		SuccessResponse response = new SuccessResponse("deleteUsers", "Users deleted successfully!");

		return ResponseEntity.ok().body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Bad request", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
