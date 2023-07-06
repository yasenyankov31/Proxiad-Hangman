package com.yasen.mvc_controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.models.UserData;

@Controller
public class UserController {
  @Autowired private UserService userService;

  @Autowired private RankingService rankingService;

  @RequestMapping("/users")
  public ModelAndView getUsersData(Integer pageNum) {
    ModelAndView modelAndView = new ModelAndView("user/users");
    if (pageNum == null) {
      pageNum = 0;
    }
    Page<UserData> users = userService.listAllUsers(pageNum);
    modelAndView.addObject("usersData", users);
    modelAndView.addObject("pageNum", pageNum);
    return modelAndView;
  }

  @RequestMapping("/userProfile")
  public ModelAndView userProfile(String username, Integer pageNum) {
    ModelAndView modelAndView = new ModelAndView("user/user_profile");
    if (pageNum == null) {
      pageNum = 0;
    }
    List<Integer> statusValues = new ArrayList<>();
    int winCount = 0;
    int lossCount = 0;
    int prevWinCount = 0;
    Page<UserRankData> userRankDatas = rankingService.getUserInfo(username, pageNum);
    Page<UserRankData> userRankDataPage = rankingService.getUserInfo(username, null);

    do {
      List<UserRankData> currentPageData = userRankDataPage.getContent();

      for (UserRankData rankData : currentPageData) {
        if (rankData.getGameStatus().equals("Won")) {
          winCount++;
          statusValues.add(++prevWinCount);
        } else {
          lossCount++;
          statusValues.add(0);
          prevWinCount = 0;
        }
      }

    } while (userRankDataPage.hasNext());

    modelAndView.addObject("username", username);
    modelAndView.addObject("winCount", winCount);
    modelAndView.addObject("lossCount", lossCount);
    modelAndView.addObject("statusValues", statusValues);
    modelAndView.addObject("userRankDatas", userRankDatas);
    modelAndView.addObject("pageNum", pageNum);

    return modelAndView;
  }

  @PostMapping("/updateUser")
  public ModelAndView createUser(@Valid UserData userData, Integer pageNum, BindingResult result) {
    if (result.hasErrors()) {
      return new ModelAndView("error")
          .addObject("errorMessage", result.getFieldError().getDefaultMessage());
    }
    userService.createOrUpdateUser(userData);
    ModelAndView modelAndView = new ModelAndView("user/users");
    Page<UserData> users = userService.listAllUsers(pageNum);
    modelAndView.addObject("usersData", users);
    modelAndView.addObject("pageNum", pageNum);
    return modelAndView;
  }

  @PostMapping("/deleteUsers")
  public ModelAndView deleteUsers(@RequestParam("ids") ArrayList<Long> ids, Integer pageNum) {
    if (ids.isEmpty()) {
      return new ModelAndView("error").addObject("errorMessage", "User ids can't be size 0");
    }
    userService.deleteUsers(ids);
    ModelAndView modelAndView = new ModelAndView("user/users");
    Page<UserData> users = userService.listAllUsers(pageNum);
    modelAndView.addObject("usersData", users);
    modelAndView.addObject("pageNum", pageNum);
    return modelAndView;
  }
}
