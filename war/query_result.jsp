<%@page import="edu.dartmouth.cs.server.db.ExerciseItem"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Entries</title>
</head>
<body>
<br>
	<b>Exercise Items</b>
	<table align="center" border="1">
				<tr>
					<th>Date</th>
					<th>Exercise Time</th>
					<th>Goal Completed</th>
					<th>Speech</th>
					<th>Accuracy</th>
				
				</tr>
	
				<%
					ArrayList<ExerciseItem> entryList = (ArrayList<ExerciseItem>) request.getAttribute("entryList");
					for (ExerciseItem entry : entryList) {
						long seconds = entry.exerciseTime / 1000;
						long minutes = seconds / 60;
						seconds = seconds % 60;
						String s = Long.toString(minutes) + " mins " + Long.toString(seconds) + " secs";
						String r = (entry.speechCorrectCount+"/"+entry.speechDoneCount);
						double speech_pct = (entry.speechCorrectCount / ((double) entry.speechDoneCount)) * 100;
						String speech_good_pct = String.format("%.2f", speech_pct) + "%";
						String d = entry.getMonth() + "/" + entry.getDate() +"/"+entry.getYear();
						String goal_completed = String.format("%.2f", 100 * entry.exerciseTime / ((double) 60*60*1000)) + "%"; 
				%> 
				
				<tr>
					<td><%=d%></td>
					<td><%=s%></td>
					<td><%=goal_completed%></td>
					<td><%=r%></td>
					<td><%=speech_good_pct%></td>
				</tr>
			
				<%
					}
				%>

	</table>

</body>
</html>
