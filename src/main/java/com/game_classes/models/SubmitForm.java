package com.game_classes.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.game_classes.interfaces.CharConstraint;

public class SubmitForm {
  @NotNull(message = "Game ID must not be blank")
  @NotEmpty(message = "Invalid game id")
  private String gameId;

  @CharConstraint
  @NotNull(message = "Letter must not be null")
  private String letter;

  public long getGameId() {
    return Long.parseLong(gameId);
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
