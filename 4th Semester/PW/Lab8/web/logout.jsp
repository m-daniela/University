<%--
  Created by IntelliJ IDEA.
  User: mosda
  Date: 12-May-20
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate();
    response.sendRedirect("index.html");
%>