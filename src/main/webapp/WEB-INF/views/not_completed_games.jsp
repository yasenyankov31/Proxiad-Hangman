<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
  <ul class="navbar">
    <li style="padding-left:100px"><a class="active" href="/">Home</a></li>
    <li><a href="/notCompletedGames">Not completed games</a></li>
    <li><a href="/users">Users</a></li>
  </ul>
  <div class="container">
  	<h1 style="padding-bottom:50px">All not finished games</h1>
    <table>
        <thead>
        <tr>
            <th>Attempts left</th>
            <th>Word progress</th>
        </tr>
        </thead>
		<tbody>
		  <c:forEach var="game" items="${unfinishedGames}">
		    <tr>
		      <td>${game.getAttemptsLeft()}</td>
		      <td>${game.getGuessedWord()}</td>
		      <td class="button-container">
		        <form action="/getGame" method="get">
		          <input type="hidden" value="1" name="letter">
		          <input type="hidden" value="${game.getId()}" name="gameId">
		          <button>Continue</button>
		        </form>
		      </td>
		    </tr>
		  </c:forEach>
		</tbody>
    </table>
  </div>
</body>
<style>
	body {
	  display: grid;
	  place-items: center;
	  font-family: 'Varela Round', sans-serif;
	  height: 100vh;
	  margin: 0; /* Remove default body margin */
	}
	.container {
	  max-width: 800px;
	  margin: 0 auto;
	}
	.navbar {
	  position: fixed;
	  top: 0;
	  left: 0;
	  width: 100%;
	  margin: 0;
	  padding: 0;
	  list-style-type: none;
	  background-color: #333;
	  overflow: hidden;
	}
	.navbar li {
	  float: left;
	}
	.navbar li a {
	  display: block;
	  color: white;
	  text-align: center;
	  padding: 14px 16px;
	  text-decoration: none;
	  font-size: 30px;
	}
	.navbar li a:hover {
	  background-color: #111;
	}
	table {
	  width: 100%;
	  border-collapse: collapse;
	}
	th,
	td {
	  padding: 8px;
	  border: 1px solid #ddd;
	  text-align: center;
	}
	th {
	  background-color: #f2f2f2;
	}
	.button-container {
	  display: flex;
	  justify-content: center;
	}
	.button-container button {
	  margin: 5px;
	}
</style>
</html>