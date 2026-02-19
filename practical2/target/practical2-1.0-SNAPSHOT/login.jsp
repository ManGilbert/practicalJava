<%-- 
    Document   : login
    Created on : Feb 18, 2026, 12:34:01 PM
    Author     : Software Engineer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        Login    </head>
    <body>
        <form action="login" method="post">
            Username: <input type="text" name="username"><br><br>
            Password: <input type="password" name="password"><br><br>
            <button type="submit">Login</button>
        </form>

        <p style="color:red">${error}</p>
    </body>
</html>
