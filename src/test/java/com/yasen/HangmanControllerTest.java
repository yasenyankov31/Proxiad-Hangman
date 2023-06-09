package com.yasen;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.testng.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import com.game_classes.interfaces.GameRepository;
import com.game_classes.interfaces.GameService;
import com.game_classes.models.Game;
import com.game_classes.models.SubmitForm;

class HangmanControllerTest {
  @Mock private GameRepository repository;

  @Mock private GameService gameService;

  @InjectMocks private HangmanController controller;

  private MockMvc mockMvc;

  private Game game;

  @BeforeEach
  public void setup() {
    game = new Game("sampleId", "example", "*******", 7, 2);
    game.setLettersUsed("a");
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void indexTest() throws Exception {
    when(gameService.createNewGame()).thenReturn(game);
    ModelAndView modelAndView =
        mockMvc
            .perform(get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getModelAndView();

    assertEquals("index", modelAndView.getViewName());
  }

  @Test
  void guessLetterTest() throws Exception {
    SubmitForm submitForm = new SubmitForm();
    submitForm.setGameId(game.getId());
    submitForm.setLetter('A');

    // Mocked binding result
    BindingResult mockBindingResult = mock(BindingResult.class);
    when(mockBindingResult.hasErrors()).thenReturn(false);

    // Mocked service method
    when(gameService.guessLetter(submitForm.getGameId(), submitForm.getLetter())).thenReturn(game);
    when(gameService.getUsersLetters(submitForm.getGameId())).thenReturn("a");

    // Perform the MVC test
    ModelAndView modelAndView =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/guess")
                    .param("gameId", submitForm.getGameId())
                    .param("letter", submitForm.getLetter() + ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("game"))
            .andReturn()
            .getModelAndView();

    // Verify that the expected methods were called
    verify(gameService, times(1)).guessLetter(submitForm.getGameId(), submitForm.getLetter());

    // Assert the model attributes
    assertEquals(modelAndView.getViewName(), "game");
    assertEquals(modelAndView.getModel().get("gameId"), game.getId());
    assertEquals(modelAndView.getModel().get("result"), "Guessed word: ******* , Attempts left: 7");
    assertEquals(modelAndView.getModel().get("lettersUsed"), "a");
    assertEquals(modelAndView.getModel().get("wordNum"), game.getWordNum());
    assertEquals(modelAndView.getModel().get("isGameOver"), false);
  }

  @Test
  void resetGameTest() throws Exception {
    SubmitForm submitForm = new SubmitForm();
    submitForm.setGameId(game.getId());
    submitForm.setLetter('0');

    // Mocked binding result
    BindingResult mockBindingResult = mock(BindingResult.class);
    when(mockBindingResult.hasErrors()).thenReturn(false);

    // Mocked service method
    when(gameService.getGameState(submitForm.getGameId())).thenReturn(game);
    when(gameService.resetGame(submitForm.getGameId())).thenReturn(game);
    when(gameService.getUsersLetters(submitForm.getGameId())).thenReturn("a");

    // Perform the MVC test
    ModelAndView modelAndView =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/reset")
                    .param("gameId", submitForm.getGameId())
                    .param("letter", submitForm.getLetter() + ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("game"))
            .andReturn()
            .getModelAndView();

    // Verify that the expected methods were called
    verify(gameService, times(1)).getGameState(submitForm.getGameId());
    verify(gameService, times(1)).resetGame(submitForm.getGameId());

    // Assert the model attributes
    assertEquals(modelAndView.getViewName(), "game");
    assertEquals(modelAndView.getModel().get("gameId"), game.getId());
    assertEquals(modelAndView.getModel().get("result"), "Guessed word: ******* , Attempts left: 7");
    assertEquals(modelAndView.getModel().get("lettersUsed"), "a");
    assertEquals(modelAndView.getModel().get("wordNum"), game.getWordNum());
    assertEquals(modelAndView.getModel().get("isGameOver"), false);
  }

  @Test
  void getGameByIdTest() throws Exception {
    SubmitForm submitForm = new SubmitForm();
    submitForm.setGameId(game.getId());
    submitForm.setLetter('0');

    // Mocked binding result
    BindingResult mockBindingResult = mock(BindingResult.class);
    when(mockBindingResult.hasErrors()).thenReturn(false);

    // Mocked service method
    when(gameService.getGameState(submitForm.getGameId())).thenReturn(game);
    when(gameService.getUsersLetters(submitForm.getGameId())).thenReturn("a");

    // Perform the MVC test
    ModelAndView modelAndView =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/getGame")
                    .param("gameId", submitForm.getGameId())
                    .param("letter", submitForm.getLetter() + ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("game"))
            .andReturn()
            .getModelAndView();

    // Verify that the expected methods were called
    verify(gameService, times(1)).getGameState(submitForm.getGameId());

    // Assert the model attributes
    assertEquals(modelAndView.getViewName(), "game");
    assertEquals(modelAndView.getModel().get("gameId"), game.getId());
    assertEquals(modelAndView.getModel().get("result"), "Guessed word: ******* , Attempts left: 7");
    assertEquals(modelAndView.getModel().get("lettersUsed"), "a");
    assertEquals(modelAndView.getModel().get("wordNum"), game.getWordNum());
    assertEquals(modelAndView.getModel().get("isGameOver"), false);
  }

  @Test
  void getGameByIdTest_invalidInput() throws Exception {
    // Mocked binding result with a field error
    BindingResult mockBindingResult = mock(BindingResult.class);
    when(mockBindingResult.hasErrors()).thenReturn(true);
    when(mockBindingResult.getFieldError())
        .thenReturn(new FieldError("submitForm", "gameId", "Invalid input"));

    // Perform the MVC test
    ModelAndView modelAndView =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/getGame")
                    .param("gameId", game.getId())
                    .param("letter", ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("error"))
            .andReturn()
            .getModelAndView();

    // Assert the model attributes
    assertEquals(modelAndView.getViewName(), "error");
    assertNotNull(modelAndView.getModel().get("errorMessage"));

    // Verify that the gameService methods were not called
    verify(gameService, never()).getGameState(anyString());
  }
}
