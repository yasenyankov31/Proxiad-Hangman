package com.yasen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.game_classes.Game;
import com.game_classes.GameService;

/** Servlet implementation class HangmanController */
@WebServlet("/HangmanController")
public class HangmanController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private GameService gameService;

  @Override
  public void init() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    gameService = context.getBean(GameService.class);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getParameter("action");
    String result = "Not existing 404!";
    boolean isGameOver = false;
    Game game = null;
    String gameId = null;

    if (action != null) {
      gameId = request.getParameter("gameId");

      switch (action) {
        case "new":
          Game newGame = gameService.createNewGame();
          gameId = newGame.getId();
          result = newGame.toString();
          break;

        case "guess":
          char letter = request.getParameter("letter").charAt(0);
          game = gameService.guessLetter(gameId, letter);
          if (game == null) {
            result = "Game not found!";
            break;
          }
          result = game.toString();
          if (result.contains("Game over") || result.contains("Game won")) {
            isGameOver = true;
          }
          request.setAttribute("lettersUsed", gameService.getUsersLetters(gameId));
          break;

        case "reset":
        case "getId":
          game = gameService.getGameState(gameId);

          if (game == null) {
            result = "Game not found!";
            break;
          }

          result =
              (action.equals("reset")) ? gameService.resetGame(gameId).toString() : game.toString();
          request.setAttribute("lettersUsed", gameService.getUsersLetters(gameId));
          break;

        default:
          break;
      }

      request.setAttribute("gameId", gameId);
    }

    request.setAttribute("result", result);
    request.setAttribute("isGameOver", isGameOver);
    request.getRequestDispatcher("IoCHangman.jsp").forward(request, response);
  }
}
