<%@ page import="web.domain.User" %>
<!DOCTYPE html>
<html>
<head>
	<title>Gallery</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="main.css">
	<script
			src="https://code.jquery.com/jquery-3.5.1.js"
			integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
			crossorigin="anonymous"></script>
</head>
<body>
	<div class="filters">
		<% User user = (User) session.getAttribute("user");
			if(user != null){
		%>
		<input type="hidden" id="userid" value=<%out.println(user.getId());%>>
		<p>Welcome, <%out.println(user.getUsername());%></p>
		<a href="profile.jsp">Add pictures</a><br>
		<button id="yourpics">Your gallery</button>
		<button id="gallery">Vote pictures</button>
		<a href="logout.jsp">Log out</a>
		<br>
		<hr>
		<br>
		<button id="top5">Top 5</button>
		<button id="top10">Top 10</button>
		<input id="in" type="text" name="input">
		<button id="show">Custom Top</button>
		<br>
		<hr>
		<br>
		<p id="current"></p>
	</div>
	<div class="container2">
		
		<div class="query">
			<div id="results">
			
				
			</div>
		</div>
<%--		<button name="more" id="more">Show more</button>--%>
	</div>

	<script type="application/javascript" src="pictures.js"></script>
	<%
		}
		else{
		out.println("<a href=\"index.html\">Log in first</a><br>\n" +
		"<a href=\"signup.html\">Create an account</a><br>");
		}
	%>

</body>
</html>