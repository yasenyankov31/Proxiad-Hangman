package com.yasen;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Servlet implementation class WordGenerator */
@WebServlet("/WordGenerator")
public class WordGenerator extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private String generateWord(String path) {
    try {
      File file = new File(path);
      Random random = new Random();

      int randInt = random.nextInt(10000);
      int index = 0;

      Scanner reader = new Scanner(file);
      while (reader.hasNextLine()) {
        String data = reader.nextLine();
        if (index == randInt) {
          return data;
        }

        index++;
      }
      reader.close();
    } catch (Exception e) {
      System.out.println("Error" + e.getMessage());
    }
    return "";
  }

  public static SecretKey createSecretKeyFromString(String secretKeyString) {
    byte[] keyBytes = new byte[16]; // AES-128 requires a 16-byte key
    byte[] originalKeyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
    System.arraycopy(
        originalKeyBytes, 0, keyBytes, 0, Math.min(originalKeyBytes.length, keyBytes.length));
    return new SecretKeySpec(keyBytes, "AES");
  }

  public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String hideWord(String input) {
    StringBuilder sb = new StringBuilder();
    char first = input.charAt(0);
    char last = input.charAt(input.length() - 1);
    sb.append(first);
    for (int i = 1; i < input.length() - 1; i++) {
      if (input.charAt(i) == first || input.charAt(i) == last) {
        sb.append(input.charAt(i));
      } else {
        sb.append("*");
      }
    }
    sb.append(last);
    return sb.toString();
  }
  /** @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    String wordListPathString =
        "C:/Users/y.yankov/eclipse-workspace/Hangman/src/main/java/wordlist.txt";

    String originalString = generateWord(wordListPathString);
    String secretKeyString = "MySecretKey123"; // Your own secret key (password)

    // Create the secret key from the string
    SecretKey secretKey = createSecretKeyFromString(secretKeyString);

    // Encrypt the string
    try {
      String encryptedString = encrypt(originalString, secretKey);
      ArrayList<String> choices = new ArrayList<>();
      session.setAttribute("secret", encryptedString);
      session.setAttribute("progress", hideWord(originalString));
      session.setAttribute("tries", encrypt("8", secretKey));
      session.setAttribute("choices", choices);

      response.sendRedirect("WordDecoder");

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e);
    }
  }
}
