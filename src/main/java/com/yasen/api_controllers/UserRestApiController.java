package com.yasen.api_controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.game_classes.interfaces.UserFactory;
import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.models.UserData;
import com.game_classes.models.dto.UserDto;
import com.game_classes.models.dto.UserProfileDto;

@RestController
@RequestMapping("/api/userController")
public class UserRestApiController {
  @Autowired private UserService userService;

  @Autowired private RankingService rankingService;

  @Autowired private UserFactory userFactory;

  @GetMapping("/users/{pageNum}")
  public ResponseEntity<List<UserDto>> getUsersData(@PathVariable Integer pageNum) {
    if (pageNum == null) {
      pageNum = 0;
    }
    Page<UserData> users = userService.listAllUsers(pageNum);

    return ResponseEntity.ok().body(userFactory.fromEntities(users.getContent()));
  }

  @GetMapping("/userProfile/{username}/{pageNum}")
  public ResponseEntity<UserProfileDto> userProfile(
      @PathVariable String username, @PathVariable(required = false) Integer pageNum) {
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

    UserProfileDto userProfileDto =
        new UserProfileDto(statusValues, userRankDatas, winCount, lossCount);

    return ResponseEntity.ok(userProfileDto);
  }

  @PatchMapping("/updateUser/{pageNum}")
  public ResponseEntity<List<UserDto>> updateUser(
      @Valid @RequestBody UserData userData, @PathVariable Integer pageNum, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(null);
    }

    userService.createOrUpdateUser(userData);
    Page<UserData> users = userService.listAllUsers(pageNum);

    List<UserDto> userDtos = userFactory.fromEntities(users.getContent());

    return ResponseEntity.ok().body(userDtos);
  }

  @DeleteMapping("/deleteUsers/{pageNum}")
  public ResponseEntity<List<UserDto>> deleteUsers(
      @RequestBody List<Long> ids, @RequestParam Integer pageNum) {
    if (ids.isEmpty()) {
      return ResponseEntity.badRequest().body(null);
    }

    userService.deleteUsers(ids);
    Page<UserData> users = userService.listAllUsers(pageNum);

    return ResponseEntity.ok().body(userFactory.fromEntities(users.getContent()));
  }
}
