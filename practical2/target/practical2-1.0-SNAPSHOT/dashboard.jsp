<%-- 
    Document   : dashboard
    Created on : Feb 18, 2026, 12:46:10 PM
    Author     : Software Engineer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard Page</title>
    </head>
    <body>
        <h2>Welcome ${sessionScope.fullname}</h2>

        <p>Username: ${sessionScope.username}</p>
        <p>Role: ${sessionScope.role}</p>

        <hr>

        <a href="students.jsp">Manage Students</a> |
        <a href="logout">Logout</a>


    </body>
</html>
