package com.game_classes.models.dto;

import java.time.LocalDate;

import com.game_classes.models.UserData;

public class UserDto {
	private long id;
	private String username;
	private String password;
	private LocalDate birthDate;
	private Integer age;

	public UserDto() {
	}

	public UserDto(UserData userData) {
		this.id = userData.getId();
		this.username = userData.getUsername();
		this.password = userData.getPassword();
		this.birthDate = userData.getBirthDate();
		this.age = userData.getAge();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
