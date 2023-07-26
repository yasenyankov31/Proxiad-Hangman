package com.yasen.rest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game_classes.models.dto.UserDto;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserRestAssuredControllerTest {
	public static final String BASE_URL = "http://localhost:8080/api/users";
	@Autowired
	ObjectMapper objectMapper;

	@Test(priority = 1)
	public void createUserTest() throws InterruptedException, JSONException {
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", "123");
		requestParams.put("username", "testAssured");
		requestParams.put("password", "testAssuredPassword");
		requestParams.put("birthDate", "2023-07-13");
		requestParams.put("age", "420");

		Response response = given().when().header("Content-Type", "application/json").body(requestParams.toJSONString())
				.post(BASE_URL);
		String jsonResponse = response.body().asString();
		response.then().statusCode(200).contentType(ContentType.JSON);
		JsonPath jsonPath = JsonPath.from(jsonResponse);
		assertEquals(jsonPath.getString("action"), "createUser");
		assertEquals(jsonPath.getString("message"), "User created successfully!");
	}

	@Test(priority = 2)
	public void getUserTest() throws JsonMappingException, JsonProcessingException {
		Response response = given().when().get(BASE_URL + "?pageNum=0");
		String jsonResponse = response.body().asString();
		JsonPath jsonPath = JsonPath.from(jsonResponse);
		String jsonContent = jsonPath.getString("content");

		int lastPageNum = jsonPath.getInt("totalPages") - 1;

		response = given().when().get(BASE_URL + "?pageNum=" + lastPageNum);
		jsonResponse = response.body().asString();
		jsonPath = JsonPath.from(jsonResponse);
		jsonContent = jsonPath.getString("content");

		List<UserDto> userDtoList = new ArrayList<>();

		Pattern pattern = Pattern.compile(
				"\\[id:(\\d+), username:(\\w+), password:(\\w+), birthDate:(\\d{4}-\\d{2}-\\d{2}), age:(\\d+)\\]");
		Matcher matcher = pattern.matcher(jsonContent);

		while (matcher.find()) {
			long id = Long.parseLong(matcher.group(1));
			String username = matcher.group(2);
			String password = matcher.group(3);
			LocalDate birthDate = LocalDate.parse(matcher.group(4));
			int age = Integer.parseInt(matcher.group(5));

			UserDto userDto = new UserDto();
			userDto.setId(id);
			userDto.setUsername(username);
			userDto.setPassword(password);
			userDto.setBirthDate(birthDate);
			userDto.setAge(age);

			userDtoList.add(userDto);
		}

		response.then().statusCode(200).contentType(ContentType.JSON);

		UserDto foundUser = userDtoList.stream().filter(person -> person.getUsername().equals("testAssured"))
				.findFirst().orElse(null);

		assertEquals(foundUser.getUsername(), "testAssured");
		assertEquals(foundUser.getPassword(), "testAssuredPassword");
		assertEquals(foundUser.getBirthDate().toString(), "2023-07-13");
		assertEquals(foundUser.getAge().toString(), "420");
	}

	@Test(priority = 3)
	public void updateUserTest() throws InterruptedException, JSONException {
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", "123");
		requestParams.put("username", "testAssured123");
		requestParams.put("password", "testAssuredPassword213");
		requestParams.put("birthDate", "2023-07-13");
		requestParams.put("age", "42");

		Response response = given().when().header("Content-Type", "application/json").body(requestParams.toJSONString())
				.put(BASE_URL);
		String jsonResponse = response.body().asString();
		response.then().statusCode(200).contentType(ContentType.JSON);
		JsonPath jsonPath = JsonPath.from(jsonResponse);
		assertEquals(jsonPath.getString("action"), "updateUser");
		assertEquals(jsonPath.getString("message"), "User updated successfully!");
	}

	@Test(priority = 4)
	public void getUserExistTest() throws InterruptedException {
		Response response = given().when().get(BASE_URL + "/user-profile/?username=testAssured&pageNum=0");
		String jsonResponse = response.body().asString();
		response.then().statusCode(200).contentType(ContentType.JSON);
		// Assert statusValues array is empty
		JsonPath jsonPath = JsonPath.from(jsonResponse);

		// Assert userRankDatas object properties
		assertEquals(jsonPath.getString("statusValues"), "[]");
		assertEquals(jsonPath.getString("winCount"), "0");
		assertEquals(jsonPath.getString("lossCount"), "0");
	}

	@Test(priority = 5)
	public void getUserNotExistTest() throws InterruptedException {
		Response response = given().when().get(BASE_URL + "/user-profile/?username=test242421");
		String jsonResponse = response.body().asString();
		response.then().statusCode(400).contentType(ContentType.JSON);
		// Assert statusValues array is empty
		JsonPath jsonPath = JsonPath.from(jsonResponse);

		// Assert userRankDatas object properties
		assertEquals(jsonPath.getString("error"), "Bad request");
		assertEquals(jsonPath.getString("message"), "Username doesn't exist!");
	}

	@Test(priority = 6)
	public void deleteUserNotExistTest() throws InterruptedException {
		Response response = given().when().delete(BASE_URL + "/234234");
		String jsonResponse = response.body().asString();
		response.then().statusCode(400).contentType(ContentType.JSON);
		// Assert statusValues array is empty
		JsonPath jsonPath = JsonPath.from(jsonResponse);

		// Assert userRankDatas object properties
		assertEquals(jsonPath.getString("error"), "Bad request");
		assertEquals(jsonPath.getString("message"), "User doesn't exist!");
	}

	@Test(priority = 7)
	public void deleteUserExistTest() throws InterruptedException {
		Response response = given().when().get(BASE_URL + "?pageNum=0");
		String jsonResponse = response.body().asString();
		JsonPath jsonPath = JsonPath.from(jsonResponse);
		String jsonContent = jsonPath.getString("content");

		int lastPageNum = jsonPath.getInt("totalPages") - 1;

		response = given().when().get(BASE_URL + "?pageNum=" + lastPageNum);
		jsonResponse = response.body().asString();
		jsonPath = JsonPath.from(jsonResponse);
		jsonContent = jsonPath.getString("content");

		List<UserDto> userDtoList = new ArrayList<>();

		Pattern pattern = Pattern.compile(
				"\\[id:(\\d+), username:(\\w+), password:(\\w+), birthDate:(\\d{4}-\\d{2}-\\d{2}), age:(\\d+)\\]");
		Matcher matcher = pattern.matcher(jsonContent);

		while (matcher.find()) {
			long id = Long.parseLong(matcher.group(1));
			String username = matcher.group(2);
			String password = matcher.group(3);
			LocalDate birthDate = LocalDate.parse(matcher.group(4));
			int age = Integer.parseInt(matcher.group(5));

			UserDto userDto = new UserDto();
			userDto.setId(id);
			userDto.setUsername(username);
			userDto.setPassword(password);
			userDto.setBirthDate(birthDate);
			userDto.setAge(age);

			userDtoList.add(userDto);
		}

		response.then().statusCode(200).contentType(ContentType.JSON);

		UserDto foundUser = userDtoList.stream().filter(person -> person.getUsername().equals("testAssured"))
				.findFirst().orElse(null);

		response = given().when().delete(BASE_URL + "/" + foundUser.getId());
		jsonResponse = response.body().asString();
		response.then().statusCode(200).contentType(ContentType.JSON);
		// Assert statusValues array is empty
		jsonPath = JsonPath.from(jsonResponse);

		// Assert userRankDatas object properties
		assertEquals(jsonPath.getString("action"), "deleteUser");
		assertEquals(jsonPath.getString("message"), "User deleted successfully!");
	}
}
