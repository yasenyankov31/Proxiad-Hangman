<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	<div class="error-message-container">
	  <span class="error-message">${errorMessage}</span>
	</div>
  <form action="/" method="get">
    <button>Back to index</button>
  </form>
</body>
<style>
.error-message {
  color: red;
  font-weight: bold;
  margin: 10px 0;
}

.error-message-container {
  border: 1px solid red;
  background-color: #ffe5e5;
  padding: 10px;
  margin-bottom: 20px;
}

</style>
</html>