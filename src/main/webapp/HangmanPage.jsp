<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import=" java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="triesValue" value="${sessionScope.triesval}" />
<c:set var="triesValueInt" value="${(triesValue == null) ? 0 : Integer.parseInt(triesValue)}" />
<c:set var="isGameOver" value="${triesValueInt lt 1}" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Guess the word</title>
</head>
<body>

 <div class="keyboard-container">
		<div >
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
			<c:choose>
			  <c:when test="${sessionScope.isWin}">
			    <form class="text" action="/Hangman/WordGenerator" method="get">
			    <span id="word"></span>
			      <p>You are right the word is <c:out value="${sessionScope.progress}" /></p>
			      <button>Try again?</button>
			    </form>
			  </c:when>
			  <c:otherwise>
			    <c:choose>
			      <c:when test="${isGameOver}">
			        <form class="text" action="/Hangman/WordGenerator" method="get">
			        		<span id="word"></span>
			          <p>You lost! The word was <c:out value="${sessionScope.progress}" /></p>
			          <button>Try again?</button>
			        </form>
			      </c:when>
			      <c:otherwise>
			        <div id="progress">
			          <div class="text">Word: <span id="word"><c:out value="${sessionScope.progress}" /></span></div>
			          <div class="text">You have <c:out value="${sessionScope.triesval}" /> tries</div>
			        </div>
			      </c:otherwise>
			    </c:choose>
			  </c:otherwise>
			</c:choose>

		


	  
 </div>
</body>
<script>
const word=document.getElementById("word").innerText
const first=word.charAt(0)
const last=word.charAt(word.length - 1)

const btns="<c:out value="${sessionScope.choices}" />".replace("[","").replace("]","").split(", ");
btns.forEach(function(element, index) {
	btns[index] = element.trim();
	});
	document.addEventListener('DOMContentLoaded', function() {
	var buttons = document.getElementsByTagName("button");
	
	for (var i = 0; i < buttons.length; i++) {
	  var button = buttons[i];
	  if((${sessionScope.isWin}||${isGameOver})&&button.innerText!="Try again?"){
		  button.disabled=true
	  }
	  else{
		  var buttonInnerText = button.innerText.toLowerCase();
		  if (btns.includes(buttonInnerText)||first==buttonInnerText||last==buttonInnerText) {
		    button.disabled=true
		  }
	  }

	}
	});



function handleButtonClick(event) {
	  var letter = event.target.innerHTML.toLowerCase();
	  var xhr = new XMLHttpRequest();
	  
	  xhr.open('GET', "/Hangman/WordDecoder?letter="+letter, true);


	  xhr.onreadystatechange = function () {
	    if (xhr.readyState === 4 && xhr.status === 200) {
	      var response = xhr.responseText;
	      console.log(response);
	      // Process the response data here
	      location.reload();
	    }
	  };

	  xhr.send();
	}
	
</script>
  <style>
  	.restart-btn{
  		margin-left:500px;
  		font-size: 40px;
        justify-content: right;
  	}
    .text {
      font-size: 40px;
      justify-content: right;
      padding-left:500px;
    }
      .keyboard-container {
      display: flex;
      justify-content: left;
      margin: 20px;
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
  </style>
</html>