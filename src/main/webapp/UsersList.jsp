<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users Management Application</title>

<body>
    <center>
        <h1>Users Management</h1>
        <h2>
            <a href="/simple/newuser">Add New User</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/simple/listusers">List All Users</a>
        </h2>
    </center>

    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Age</th>
            </tr>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.surname}" /></td>
                    <td><c:out value="${user.age}" /></td>
                    <td>
                        <a href="/simple/edit?id=<c:out value='${user.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/simple/delete?id=<c:out value='${user.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>

</head>