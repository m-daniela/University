<%@ page import="web.domain.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add picture</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="main.css">
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <% User user = (User) session.getAttribute("user");
        if(user != null){
    %>
    <p>Welcome, <%out.println(user.getUsername());%></p>


    <a href="gallery.jsp" >Gallery</a>
    <form class="add" action="PictureController" method="post">
        <input type="hidden" id="userid" name="userid" value=<% out.println(user.getId());%>>
        <label>Attach a file: </label><br><input type="file" name="file">
        <p>Or</p>
        <label>Add URL: </label><br><input type="text" name="url" placeholder="URL..."><br>
        <button type="submit">Add picture</button>
    </form>

<%--    <script type="application/javascript" src="pictures.js"></script>--%>

    <a href="logout.jsp">Log out.</a>
    <%
        }
        else{
            out.println("<a href=\"index.html\">Log in first</a><br>\n" +
                    "<a href=\"signup.html\">Create an account</a><br>");
        }
    %>

</div>
<script>
    $(".container").on("submit", ".add", function (e) {
        e.preventDefault();
        let userid = $("#userid").val();
        let url = $("input[name=url]").val();
        let filename = $("input[name=file]").val();
        let file = filename.substring(filename.lastIndexOf('\\') + 1);
        if (url == "" && file == "") {
            alert("Please add a file");
        }
        else {
            $.post("PictureController", {
                action: "add",
                userid: userid,
                file: file,
                url: url
            }, function () {
                alert("Picture added");
                $("input[name=file]").val("");
                $("input[name=url]").val("");
            });
        }

    });
</script>

</body>
</html>