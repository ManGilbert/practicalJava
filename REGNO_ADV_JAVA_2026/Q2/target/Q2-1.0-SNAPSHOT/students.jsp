<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Student Registration</title>
</head>
<body>

<c:if test="${not empty welcome}">
    <h1>${welcome}</h1>
</c:if>

<c:if test="${not empty successfully}">
    <h3>${successfully}</h3>
</c:if>

<h2>Student Insert</h2>

<form action="StudentServlet" method="post">
    Name: <input type="text" name="name" required /> <br><br>
    Course: <input type="text" name="course" required /> <br><br>
    <input type="submit" value="Save Student" />
</form>

<br>

<h2>All Students</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Course</th>
    </tr>

    <c:forEach var="s" items="${students}">
        <tr>
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>${s.course}</td>
        </tr>
    </c:forEach>
</table>

<br>

<h2>Subjects</h2>
<ul>
    <c:forEach var="sub" items="${subjects}">
        <li>${sub}</li>
    </c:forEach>
</ul>

</body>
</html>
