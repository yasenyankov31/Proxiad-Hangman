<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hangman</title>
<style>
  body {
    text-align: center;
    padding-top: 50vh;
  }
  form {
    display: block;
    margin-bottom: 10px;
  }
</style>
</head>
<body>
 
  <form action="getGame" method="get">
    <input type="text" id="gameId" name="gameId">
    <input type="hidden" value="1" id="letter" name="letter">
    <button>Search for single game by id</button>
  </form>
   <br>
     <form action="joinPartyGameById" method="get">
    <input type="text" id="gameId" name="gameId">
    <input type="hidden" value="1" id="letter" name="letter">
    <button>Search for party game by id</button>
  </form>
  <br>
  <form action="new" method="get">
    <button>Start game</button>
  </form>
   <form action="createParty" method="get">
    <button>Create party</button>
  </form>

</body>
</html>
