package com.game_classes.models;

import java.time.LocalDate;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserData {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private long id;

  private String username;

  private String password;

  private LocalDate birthDate;

  private Integer age;

  public UserData() {
    Random random = new Random();
    int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);

    this.birthDate = LocalDate.ofEpochDay(randomDay);
    this.age = random.nextInt(31) + 20;
    this.password = "password";
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
