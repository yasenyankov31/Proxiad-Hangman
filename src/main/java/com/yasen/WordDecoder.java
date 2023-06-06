package com.yasen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Servlet implementation class WordDecoder */
@WebServlet("/WordDecoder")
public class WordDecoder extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public static SecretKey createSecretKeyFromString(String secretKeyString) {
    byte[] keyBytes = new byte[16]; // AES-128 requires a 16-byte key
    byte[] originalKeyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
    System.arraycopy(
        originalKeyBytes, 0, keyBytes, 0, Math.min(originalKeyBytes.length, keyBytes.length));
    return new SecretKeySpec(keyBytes, "AES");
  }

  public static String decrypt(String encryptedText, SecretKey secretKey)
      throws Exception, NumberFormatException {
    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
    return new String(decryptedBytes, StandardCharsets.UTF_8);
  }

  public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String changeProgress(String progress, char letter, String originalWord) {
    char[] progressArray = progress.toCharArray();
    for (int i = 0; i < progressArray.length; i++) {
      if (originalWord.charAt(i) == letter) {
        progressArray[i] = letter;
      }
    }
    return new String(progressArray);
  }

  /** @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    String secretKeyString = "MySecretKey123"; // Your own secret key (password)

    // Create the secret key from the string
    SecretKey secretKey = createSecretKeyFromString(secretKeyString);
    String encryptedWord = session.getAttribute("secret").toString();
    String decryptedTries = null;
    try {
      decryptedTries = decrypt(session.getAttribute("tries").toString(), secretKey);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Integer tries = Integer.parseInt(decryptedTries);
    String progress = session.getAttribute("progress").toString();
    ArrayList<String> choices = (ArrayList<String>) session.getAttribute("choices");
    session.setAttribute("isWin", false);
    try {
      String originalWord = decrypt(encryptedWord, secretKey);
      if (request.getParameter("letter") != null) {
        String letter = request.getParameter("letter");

        choices.add(" " + letter + " ");
        String newProgress = changeProgress(progress, letter.charAt(0), originalWord);
        if (newProgress.equals(progress)) {
          tries--;
        } else {
          if (originalWord.equals(newProgress)) {
            session.setAttribute("progress", originalWord);
            session.setAttribute("isWin", true);
          } else {
            session.setAttribute("progress", newProgress);
          }
        }
      }

      session.setAttribute("secret", encryptedWord);
      session.setAttribute("tries", encrypt(tries.toString(), secretKey));
      session.setAttribute("triesval", tries);
      session.setAttribute("choices", choices);
      if (tries <= 0) {
        session.setAttribute("progress", originalWord);
      }

      response.sendRedirect("HangmanPage.jsp");
      //      PrintWriter out = response.getWriter();
      //      out.print(tries);

    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
