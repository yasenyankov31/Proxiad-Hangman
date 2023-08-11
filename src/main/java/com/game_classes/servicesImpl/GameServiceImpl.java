package com.game_classes.servicesImpl;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.game_classes.interfaces.GameRepository;
import com.game_classes.interfaces.jpaRepositories.UserRepository;
import com.game_classes.interfaces.services.GameService;
import com.game_classes.interfaces.services.UserService;
import com.game_classes.models.UserData;
import com.game_classes.models.game.Game;

@Service
public class GameServiceImpl implements GameService {

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private UserRepository userRepository;

  private Random random;

  public GameServiceImpl() {
    this.random = new Random();
  }

  @Override
  public String getRandomWord() {
    try {
      File file = new File("wordlist.txt");

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
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return "Error " + ex.getLocalizedMessage();
    }
    return null;
  }

  @Override
  public Game createNewGame() {
    Subject subject = SecurityUtils.getSubject();
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
    long min = 100L;
    long max = 99999999999L;

    long gameId = random.nextLong() % (max - min + 1) + min;
    Game game = new Game(gameId, word, guessedWord.toString(), 8, Integer.parseInt(wordAndNum[1]));
    var userOptional = userRepository.findByUsername((String) subject.getPrincipal());
    if (userOptional.isPresent()) {
      game.setUserGame(userOptional.get());
    }

    gameRepository.createGame(game);
    return game;
  }

  @Override
  public Game guessLetter(long gameId, char letter) {
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
    gameRepository.updateGame(game);

    return game;
  }

  @Override
  public Game getGameState(long gameId) {
    return gameRepository.findById(gameId);
  }

  @Override
  public String getUsersLetters(long gameId) {
    Game game = gameRepository.findById(gameId);
    return game.getLetters();
  }

  @Override
  public Game resetGame(long gameId) {

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
      game.setAttemptsLeft(8);
      game.setWordNum(Integer.parseInt(wordAndNum[1]));
      gameRepository.updateGame(game);
    }
    return game;
  }

  @Override
  public void addOponentId(long id, Game userGame) {
    gameRepository.updateGame(userGame);
  }

  @Override
  public void addToQueue(Game game) {
    gameRepository.addToQueue(game.getId());
  }

  @Override
  public long getFromQueue() {
    return gameRepository.getFromQueue();
  }

  @Override
  public Page<Game> getUnfinishedGames(int pageNum) {
    Long userId = 0L;
    Subject subject = SecurityUtils.getSubject();
    var userOptional = userRepository.findByUsername((String) subject.getPrincipal());
    if (userOptional.isPresent()) {
      userId = userOptional.get().getId();
    }
    PageRequest pageable = PageRequest.of(pageNum, 5);
    return gameRepository.getUnfinishedGames(userId, pageable);
  }

  @Override
  public List<Game> getUnfinishedGames() {
    return gameRepository.getUnfinishedGames();
  }

  @Override
  public boolean checkIfGameIsCompleted(long gameId) {
    return gameRepository.checkIfGameIsCompleted(gameId);
  }
}
