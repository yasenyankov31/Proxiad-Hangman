package com.yasen.api_controllers;


import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.game_classes.interfaces.jpaRepositories.UserRepository;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.models.ErrorResponse;
import com.game_classes.models.LoginRequest;
import com.game_classes.models.UserData;
import com.game_classes.models.dto.UserDto;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AccountController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    Subject subject = SecurityUtils.getSubject();
    var userOptional = userRepository.findByUsername(loginRequest.getUsername());

    if (userOptional.isEmpty()) {
      throw new IllegalArgumentException("User doesn't exist!");
    }

    UserData user = userOptional.get();
    if (!user.getPassword().equals(loginRequest.getPassword())) { // I've changed this condition to
                                                                  // check if they're not equal.
      throw new IllegalArgumentException("Password doesn't match!");
    }

    if (!subject.isAuthenticated()) {
      UsernamePasswordToken token =
          new UsernamePasswordToken(loginRequest.getUsername(), loginRequest.getPassword());
      subject.login(token);

      UserDto response = new UserDto();
      response.setUsername(user.getUsername());
      response.setRole(user.getRole());

      return ResponseEntity.ok(response);
    } else {
      throw new IllegalArgumentException("Already logged in");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody UserData user, BindingResult result) {
    Subject subject = SecurityUtils.getSubject();
    if (result.hasErrors()) {
      throw new IllegalArgumentException("User formdata is invalid!");
    }
    if (!userService.checkIfUserExist(user.getUsername())) {
      throw new IllegalArgumentException("User with this name already exists!");
    }

    user.setRole("user");

    userService.createUser(user);


    UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
    subject.login(token);

    UserDto response = new UserDto();
    response.setUsername(user.getUsername());
    response.setRole(user.getRole());

    return ResponseEntity.ok(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    Subject subject = SecurityUtils.getSubject();

    if (subject.isAuthenticated()) {
      subject.logout();
      return ResponseEntity.ok("Logged out successfully");
    } else {
      throw new IllegalArgumentException("Not logged in");
    }
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
    ErrorResponse errorResponse = new ErrorResponse("Bad request", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
