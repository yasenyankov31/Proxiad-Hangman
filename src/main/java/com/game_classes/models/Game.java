package com.game_classes.models;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {
  @Id private long id;

  private String word;
  private String guessedWord;
  private String lettersUsed;
  private int attemptsLeft;
  private int wordNum;
  private long opponentId;
  private Date date;

  public Game() {}

  public Game(long id, String word, String guessedWord, int attemptsLeft, int wordNum) {
    super();
    this.id = id;
    this.word = word;
    this.guessedWord = guessedWord;
    this.setAttemptsLeft(attemptsLeft);
    this.lettersUsed = "";
    this.wordNum = wordNum;
    Calendar today = Calendar.getInstance();
    this.date = today.getTime();
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setLettersUsed(String lettersUsed) {
    this.lettersUsed = lettersUsed;
  }

  public String getLetters() {
    return lettersUsed;
  }

  public void addLetter(String lettersUsed) {
    this.lettersUsed += lettersUsed;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getGuessedWord() {
    return guessedWord;
  }

  public void setGuessedWord(String guessedWord) {
    this.guessedWord = guessedWord;
  }

  public int getAttemptsLeft() {
    return attemptsLeft;
  }

  public void setAttemptsLeft(int attemptsLeft) {
    this.attemptsLeft = attemptsLeft;
  }

  public int getWordNum() {
    return wordNum;
  }

  public void setWordNum(int wordNum) {
    this.wordNum = wordNum;
  }

  public long getOpponentId() {
    return opponentId;
  }

  public void setOpponentId(long opponentId) {
    this.opponentId = opponentId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getInfo() {
    if (attemptsLeft == 0) {
      return "Game over! The word was " + word;
    } else if (this.word.equals(this.guessedWord)) {
      return "Game won! The word was " + word;
    }
    return "Guessed word: " + this.guessedWord + " , Attempts left: " + this.attemptsLeft;
  }
}
