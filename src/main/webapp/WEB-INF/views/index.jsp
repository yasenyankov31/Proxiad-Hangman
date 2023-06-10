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
    grid-template-columns: repeat(2, 1fr); /* Adjust the number of columns here */
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


  </div>
</body>
</html>
