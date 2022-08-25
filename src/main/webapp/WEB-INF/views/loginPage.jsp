<%--
  Created by IntelliJ IDEA.
  User: paulinabrzecka
  Date: 24/08/2022
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Login Page</title>
    <h1>Please login to continue</h1>
</head>
<body>

<form action="LoginServlet" method="post">

    Username: <input type="text" name="username">
    <br>
    Password: <input type="password" name="pwd">
    <br><br>
    <input type="submit" value="Login">
</form>
</body>
</html>
