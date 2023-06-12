package com.game_classes;

import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.game_classes.interfaces.GameRepository;
import com.game_classes.interfaces.GameService;
import com.game_classes.models.Game;

@Component
public class GameServiceImpl implements GameService {

  @Autowired private GameRepository gameRepository;
  private Random random;

  public GameServiceImpl() {
    this.random = new Random();
  }

  @Override
  public String getRandomWord() {
    String path = "C:/Users/y.yankov/eclipse-workspace/Hangman/src/main/java/wordlist.txt";
    try {
      File file = new File(path);

      int randInt = random.nextInt(10000);
      int index = 0;

      try (Scanner reader = new Scanner(file)) {
        while (reader.hasNextLine()) {
          String data = reader.nextLine();
          if (index == randInt) {
            while (data.length() < 5) {
              data = reader.nextLine();
              index++;
            }

            return data + " " + index;
          }

          index++;
        }
      }
    } catch (Exception e) {
      return "Error" + e.getMessage();
    }
    return null;
  }

  @Override
  public Game createNewGame() {
    String gameId = UUID.randomUUID().toString();

    var wordAndNum = getRandomWord().split(" ");
    String word = wordAndNum[0];
    StringBuilder guessedWord = new StringBuilder();
    char first = word.charAt(0);
    char last = word.charAt(word.length() - 1);
    guessedWord.append(first);
    for (int i = 1; i < word.length() - 1; i++) {
      if (word.charAt(i) == first || word.charAt(i) == last) {
        guessedWord.append(word.charAt(i));
      } else {
        guessedWord.append("*");
      }
    }
    guessedWord.append(last);

    Game game = new Game(gameId, word, guessedWord.toString(), 7, Integer.parseInt(wordAndNum[1]));
    gameRepository.save(game);
    return game;
  }

  @Override
  public Game guessLetter(String gameId, char letter) {
    Game game = gameRepository.findById(gameId);
    if (game == null) {
      return null;
    }

    String word = game.getWord();
    String guessedWord = game.getGuessedWord();
    int attemptsLeft = game.getAttemptsLeft();

    letter = Character.toLowerCase(letter);

    game.addLetter("" + letter);

    if (word.indexOf(letter) == -1) {
      attemptsLeft--;
    }

    StringBuilder newGuessedWord = new StringBuilder();
    var guessArr = guessedWord.toCharArray();
    var wordArr = word.toCharArray();
    for (int i = 0; i < word.length(); i++) {
      if (wordArr[i] == letter) {
        newGuessedWord.append(wordArr[i]);
      } else {
        newGuessedWord.append(guessArr[i]);
      }
    }

    game.setGuessedWord(newGuessedWord.toString());
    game.setAttemptsLeft(attemptsLeft);
    gameRepository.save(game);

    return game;
  }

  @Override
  public Game getGameState(String gameId) {
    Game game = gameRepository.findById(gameId);
    if (game == null) {
      return null;
    }
    return game;
  }

  @Override
  public String getUsersLetters(String gameId) {
    Game game = gameRepository.findById(gameId);
    return game.getLetters();
  }

  @Override
  public Game resetGame(String gameId) {

    Game game = gameRepository.findById(gameId);
    if (game != null) {
      var wordAndNum = getRandomWord().split(" ");
      String word = wordAndNum[0];

      StringBuilder guessedWord = new StringBuilder();
      char first = word.charAt(0);
      char last = word.charAt(word.length() - 1);
      guessedWord.append(first);
      for (int i = 1; i < word.length() - 1; i++) {
        if (word.charAt(i) == first || word.charAt(i) == last) {
          guessedWord.append(word.charAt(i));
        } else {
          guessedWord.append("*");
        }
      }
      guessedWord.append(last);

      game.setWord(word);
      game.setLettersUsed("");
      game.setGuessedWord(guessedWord.toString());
      game.setAttemptsLeft(7);
      game.setWordNum(Integer.parseInt(wordAndNum[1]));
      gameRepository.save(game);
    }
    return game;
  }

  @Override
  public void addOponentId(String id, Game userGame) {
    userGame.setOpponentId(id);
    gameRepository.save(userGame);
  }

  @Override
  public void addToQueue(Game game) {
    gameRepository.addToQueue(game.getId());
  }

  @Override
  public String getFromQueue() {
    return gameRepository.getFromQueue();
  }
}
