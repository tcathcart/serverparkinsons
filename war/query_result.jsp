<%@page import="tdc.myruns.server.db.ExerciseEntry"%>
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
	<b>Exercise Entries</b>
	<table border="1">
				<tr>
					<th>Id:</th>
					<th>Date:</th>
					<th>Duration:</th>
					<th>Climb:</th>
					<th>Calories:</th>
				</tr>
	
				<%
					ArrayList<ExerciseEntry> entryList = (ArrayList<ExerciseEntry>) request
							.getAttribute("entryList");
					for (ExerciseEntry entry : entryList) {
				%> 
				
				<tr>
					<td><%=entry.mId%></td>
					<td><%=entry.mDateString%>
					<td><%=entry.mDuration%></td>
					<td><%=entry.mClimb%></td>
					<td><%=entry.mCalorie%></td>
					<td><a href="/delete.do?id=<%=entry.mId%>">delete.do</a></td>
				</tr>
			
				<%
					}
				%>

	</table>

</body>
</html>
