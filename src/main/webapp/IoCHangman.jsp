<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import=" java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Guess the word</title>
</head>
<body>
 <div class="container">

 	<div class="keyboard-container">
 	     <div id="progress">
 			<p class="text">${result}</p>
		  </div>
  		<c:choose>
	      <c:when test="${isGameOver}">
				<button onclick="newGameButton()">Try again</button>
	      </c:when>
	      <c:otherwise>
				<button onclick="resetButton()">Reset word</button>
				<button onclick="newGameButton()">New game</button>
				<button onclick="copyGameId()">Copy game id</button>
	      </c:otherwise>
	    </c:choose>
		<div id="keyboard-div">
			 <div class="keyboard">
				
				<button onclick="handleButtonClick(event)">A</button>
				<button onclick="handleButtonClick(event)">B</button>
				<button onclick="handleButtonClick(event)">C</button>
				<button onclick="handleButtonClick(event)">D</button>
				<button onclick="handleButtonClick(event)">E</button>
				<button onclick="handleButtonClick(event)">F</button>
				<button onclick="handleButtonClick(event)">G</button>
				<button onclick="handleButtonClick(event)">H</button>
				<button onclick="handleButtonClick(event)">I</button>
				<button onclick="handleButtonClick(event)">J</button>
				<button onclick="handleButtonClick(event)">K</button>
				<button onclick="handleButtonClick(event)">L</button>
				<button onclick="handleButtonClick(event)">M</button>
				<button onclick="handleButtonClick(event)">N</button>
				<button onclick="handleButtonClick(event)">O</button>
				<button onclick="handleButtonClick(event)">P</button>
				<button onclick="handleButtonClick(event)">Q</button>
				<button onclick="handleButtonClick(event)">R</button>
				<button onclick="handleButtonClick(event)">S</button>
				<button onclick="handleButtonClick(event)">T</button>
				<button onclick="handleButtonClick(event)">U</button>
				<button onclick="handleButtonClick(event)">V</button>
				<button onclick="handleButtonClick(event)">W</button>
				<button onclick="handleButtonClick(event)">X</button>
				<button onclick="handleButtonClick(event)">Y</button>
				<button onclick="handleButtonClick(event)">Z</button>
			  </div>
		</div>

</div>			


	  
 </div>
</body>
  <script>
  	  const isError="${result}".includes("Game not found!")||"${result}".includes("Not existing 404!")
	  const isOver=${isGameOver}
  	  if(isError){
  		const keyboardDiv = document.getElementById('keyboard-div');
  		keyboardDiv.remove();
  	  }
	  const word="${result}".split(" ")[2]+"${lettersUsed}"
	  const btns = [];

	  for (let i = 0; i < word.length; i++) {
		  if(word.charAt(i)!='*'){
			 btns.push(word.charAt(i));
		  }
	  }
  
  
  	const keyboardElements = document.querySelectorAll('.keyboard > *');
	
	for (var i = 0; i < keyboardElements.length; i++) {
		  var button = keyboardElements[i];
		  var buttonInnerText = button.innerText.toLowerCase();
		  if (btns.includes(buttonInnerText)||isOver) {
		    button.disabled=true
		  }
	  }
  
  
  
  function handleButtonClick(event) {
	  var letter = event.target.innerHTML.toLowerCase();
	  window.location.href = "/Hangman/HangmanController?action=guess&gameId=${gameId}&letter="+letter;
	}
  function resetButton() {
	  window.location.href = "/Hangman/HangmanController?action=reset&gameId=${gameId}";
	}
  function newGameButton() {
	  window.location.href = "/Hangman/HangmanController?action=new";
	}
  function copyGameId() {
	  navigator.clipboard.writeText("${gameId}");

	}
  </script>
<style>
    .container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .keyboard-container {
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .keyboard {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        grid-gap: 5px;
        margin: 20px;
    }
    .keyboard button {
        width: 60px;
        height: 60px;
        font-size: 20px;
    }
    .text {
        font-size: 40px;
    }
    .restart-btn {
        margin-top: 20px;
        font-size: 40px;
    }
</style>
</html>