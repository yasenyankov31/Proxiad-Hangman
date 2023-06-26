<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Username form</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style>
  body {
    display: grid;
    place-items: center;
    height: 100vh;
  }
  .grid-container {
    display: grid;
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
         <form action="endingResult" method="get">
	      <input required type="text" id="username" name="username">
	      <input value="${gameId}" type="hidden" id="gameId" name="gameId">
	      <button>Enter your username</button>
	    </form>
    </div>


  </div>
</body>
</html>