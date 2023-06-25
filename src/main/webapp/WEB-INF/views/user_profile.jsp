<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    <title>Player ${username} statistics</title>

</head>
<body>

<div class="container">
	<h1>Player ${username} statistics</h1>
    <div class="grid">
        <div style="margin-top:50px">
            <!-- Content for the first column of the grid -->
            <h2>Player progression</h2>
            <canvas id="lineChart" style="width:100%;max-width:600px;max-height:800px"></canvas>
        </div>
        <div  style="padding-left: 100px;padding-bottom: 100px;">
            <!-- Content for the second column of the grid -->
            <h2>Player win/lose ration</h2>
            <canvas id="pieChart" style="width:100%;max-width:300px"></canvas>
        </div>
    </div>
	<h1>All played games</h1>
    <table>
        <thead>
        <tr>
       		<th>Status of game</th>
            <th>Generated word</th>
            <th>Letters used</th>
            <th>Attempts used</th>
            <th>Game creation date </th>
        </tr>
        </thead>
        <tbody>
           <c:forEach var="data" items="${userRankDatas}">
                <tr>
                	 <td>${data.getGameStatus()}</td>
			         <td>${data.getWord()}</td>
			         <td>${data.getLettersUsed()}</td>
			         <td>${data.getAttemptsLeft()}</td>
			         <td>${data.getStartDate()}</td>
			     </tr>
            </c:forEach>

        </tbody>
    </table>
</div>
</body>
  <style>
      .container {
          max-width: 800px;
          margin: 0 auto;
      }

      .grid {
          display: grid;
          grid-template-columns: 1fr 1fr;
          grid-gap: 10px;
      }

      table {
          width: 100%;
          border-collapse: collapse;
      }

      th, td {
          padding: 8px;
          border: 1px solid #ddd;
      }

      th {
          background-color: #f2f2f2;
      }
  </style>
<script>
function generateAscendingArray(highestNumber) {
	  var result = [];
	  for (var i = 1; i <= highestNumber; i++) {
	    result.push(i);
	  }
	  return result;
	}
const xValues = generateAscendingArray(${winCount}+${lossCount})

let lineChart=new Chart("lineChart", {
  type: "line",
  data: {
    labels: xValues,
    datasets: [ { 
      data: ${statusValues},
      borderColor: "blue",
      fill: false
    }]
  },
  options: {
    legend: {display: false}
  }
});
var gameStat = ["Wins", "Losses"];
var statValues = [${winCount},${lossCount}];
var barColors = [
	"#1e7145",
  "#b91d47"
];

let pieChart=new Chart("pieChart", {
  type: "pie",
  data: {
    labels: gameStat,
    datasets: [{
      backgroundColor: barColors,
      data: statValues
    }]
  }
});



</script>
</html>
