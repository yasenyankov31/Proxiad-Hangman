package com.game_classes.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubmitForm {
  @NotEmpty(message = "Game ID must not be blank")
  private String gameId;

  @NotNull(message = "Letter must not be null")
  private Character letter;

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public Character getLetter() {
    return letter;
  }

  public void setLetter(Character letter) {
    this.letter = letter;
  }
}
