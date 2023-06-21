package com.game_classes.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.game_classes.interfaces.CharConstraint;

public class SubmitForm {
  @NotEmpty(message = "Game ID must not be blank")
  private String gameId;

  @CharConstraint
  @NotNull(message = "Letter must not be null")
  private String letter;

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public Character getLetter() {
    return letter.charAt(0);
  }

  public void setLetter(String letter) {
    this.letter = letter;
  }
}
