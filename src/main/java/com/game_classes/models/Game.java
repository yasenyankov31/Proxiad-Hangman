package com.game_classes.models;

public class Game {
  private String id;
  private String word;
  private String guessedWord;
  private String lettersUsed;
  private int attemptsLeft;
  private int wordNum;
  private String opponentId;

  public Game(String id, String word, String guessedWord, int attemptsLeft, int wordNum) {
    super();
    this.id = id;
    this.word = word;
    this.guessedWord = guessedWord;
    this.setAttemptsLeft(attemptsLeft);
    this.lettersUsed = "";
    this.wordNum = wordNum;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getOpponentId() {
    return opponentId;
  }

  public void setOpponentId(String opponentId) {
    this.opponentId = opponentId;
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
