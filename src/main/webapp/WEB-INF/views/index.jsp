<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ page import=" java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hangman</title>
<style>
  body {
    display: grid;
    place-items: center;
    height: 100vh;
  }
  .grid-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr); /* Adjust the number of columns here */
    grid-gap: 10px; /* Adjust the gap between items here */
  }
  .grid-item {
    background-color: #f2f2f2;
    padding: 20px;
    text-align: center;
  }
  form {
    display: block;
    margin-bottom: 10px;
  }
  .list-box {
    height: 200px; /* Adjust the height of the list box here */
    overflow: auto;
    border: 1px solid #ccc;
    padding: 5px;
  }
</style>
</head>
<body>
  <div class="grid-container">
    <div class="grid-item">
      <form action="getGame" method="get">
        <input type="text" id="gameId" name="gameId">
        <input type="hidden" value="1" id="letter" name="letter">
        <button>Search for single game by id</button>
      </form>
      <form action="new" method="get">
        <button>Create single game</button>
      </form>
    </div>
    <div class="grid-item">
      <form action="joinPartyGameById" method="get">
        <input type="text" id="gameId" name="gameId">
        <input type="hidden" value="1" id="letter" name="letter">
        <button>Search for party game by id</button>
      </form>
      <form action="createParty" method="get">
        <button>Create party game</button>
      </form>
      <form action="joinRandomPartyGame" method="get">
        <button>Join random party game</button>
      </form>
    </div>
    <div class="grid-item">
      <div class="list-box">
        <ul>
          <c:forEach var="item" items="${list}">
            <li>${item.gameStatus}:</li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
</body>
</html>
