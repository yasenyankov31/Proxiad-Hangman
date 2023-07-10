package com.yasen.rest;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

class UserRestControllerTest {
  public static final String BASE_URL = "http://localhost:8080/api/userController";

  @Test
  public void getUsersData() {
    given().get(BASE_URL + "/users/0").then().statusCode(200);
  }
}
