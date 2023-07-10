package com.yasen.rest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.game_classes.models.dto.UserDto;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

class UserRestAssuredControllerTest {
  public static final String BASE_URL = "http://localhost:8080/api/userController";
  @Autowired ObjectMapper objectMapper;

  @Test
  public void getUsersDataTest() {
    Response response = given().when().get(BASE_URL + "/users/0");
    String jsonResponse = response.body().asString();

    UserDto[] userDtos = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      userDtos = mapper.readValue(jsonResponse, UserDto[].class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<UserDto> userDtosList = Arrays.asList(userDtos);
    UserDto foundUser =
        userDtosList.stream()
            .filter(person -> person.getUsername().equals("qska"))
            .findFirst()
            .orElse(null);

    assertNotNull(foundUser);

    response.then().statusCode(200).contentType(ContentType.JSON);
  }

  @Test
  public void userProfileTest() throws InterruptedException {
    Response response = given().when().get(BASE_URL + "/userProfile/test242421/0");
    String jsonResponse = response.body().asString();

    // Assert statusValues array is empty
    JsonPath jsonPath = JsonPath.from(jsonResponse);
    List<String> statusValues = jsonPath.getList("statusValues");
    assertEquals(statusValues.size(), 0);

    // Assert userRankDatas object properties
    assertEquals(new ArrayList<>(), jsonPath.getList("userRankDatas.content"));
    assertEquals(jsonPath.getBoolean("userRankDatas.pageable.sort.empty"), true);
    assertEquals(jsonPath.getBoolean("userRankDatas.pageable.sort.unsorted"), true);
    assertEquals(jsonPath.getBoolean("userRankDatas.pageable.sort.sorted"), false);
    assertEquals(jsonPath.getInt("userRankDatas.pageable.offset"), 0);
    assertEquals(jsonPath.getInt("userRankDatas.pageable.pageNumber"), 0);
    assertEquals(jsonPath.getInt("userRankDatas.pageable.pageSize"), 5);
    assertEquals(jsonPath.getBoolean("userRankDatas.pageable.paged"), true);
    assertEquals(jsonPath.getBoolean("userRankDatas.pageable.unpaged"), false);
    assertEquals(jsonPath.getBoolean("userRankDatas.last"), true);
    assertEquals(jsonPath.getInt("userRankDatas.totalPages"), 0);
    assertEquals(jsonPath.getInt("userRankDatas.totalElements"), 0);
    assertEquals(jsonPath.getInt("userRankDatas.size"), 5);
    assertEquals(jsonPath.getInt("userRankDatas.number"), 0);
    assertEquals(jsonPath.getBoolean("userRankDatas.sort.empty"), true);
    assertEquals(jsonPath.getBoolean("userRankDatas.sort.unsorted"), true);
    assertEquals(jsonPath.getBoolean("userRankDatas.sort.sorted"), false);
    assertEquals(jsonPath.getBoolean("userRankDatas.first"), true);
    assertEquals(jsonPath.getInt("userRankDatas.numberOfElements"), 0);
    assertEquals(jsonPath.getBoolean("userRankDatas.empty"), true);

    // Assert winCount and lossCount
    assertEquals(jsonPath.getInt("winCount"), 0);
    assertEquals(jsonPath.getInt("lossCount"), 0);
  }
}
