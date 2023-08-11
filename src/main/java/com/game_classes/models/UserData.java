package com.game_classes.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import com.game_classes.models.game.Game;

@Entity
public class UserData {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;
  @NotBlank(message = "Username can't be blank")
  private String username;
  @NotBlank(message = "Password can't be blank")
  private String password;
  @NotNull(message = "Birth date can't be null")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;
  @NotNull(message = "Age can't be null")
  private Integer age;

  private String role;
  @OneToMany(mappedBy = "userGame", cascade = CascadeType.ALL)
  private List<Game> userGame;


  public UserData() {
    Random random = new Random();
    int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);

    this.birthDate = LocalDate.ofEpochDay(randomDay);
    this.age = random.nextInt(31) + 20;
    this.password = "password";
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
