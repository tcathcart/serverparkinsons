<%@page import="edu.dartmouth.cs.server.db.ExerciseItem"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Entries</title>
</head>
<body>
	<b>Exercise Items</b>
	<table border="1">
				<tr>
					<th>Date</th>
					<th>Exercise Time</th>
					<th>Speech</th>
				
				</tr>
	
				<%
					ArrayList<ExerciseItem> entryList = (ArrayList<ExerciseItem>) request
							.getAttribute("entryList");
					for (ExerciseItem entry : entryList) {
						String s = Long.toString(entry.exerciseTime);
						String r = (entry.speechCorrectCount+"/"+entry.speechDoneCount);
				%> 
				
				<tr>
					<td><%=entry.date %></td>
					<td><%=s%></td>
					<td><%=r%></td>
				</tr>
			
				<%
					}
				%>

	</table>

</body>
</html>
