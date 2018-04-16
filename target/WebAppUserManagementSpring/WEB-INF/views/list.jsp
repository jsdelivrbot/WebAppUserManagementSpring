<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
    <!-- Material Design fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- Bootstrap Material Design -->
    <link rel="stylesheet" href="https://cdn.rawgit.com/FezVrasta/bootstrap-material-design/dist/dist/bootstrap-material-design.min.css">

</head>

<body>
<nav class="navbar navbar-light bg-faded">
    <a class="navbar-brand">Users Management App</a>
    <ul class="nav navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="<c:url value="/newUser" />"><button class="btn btn-outline-info"> Add New User</button></a>
        </li>
    </ul>

        <c:url var="searchUrl" value="/users"/>
        <form:form method="GET" action="${searchUrl}" cssClass="form-inline pull-xs-right">
        <div class="form-group">
            <input type="text" class="form-control" size="40" maxlength="40" name="term" placeholder="Search for firstname, lastname or country"/></br>
        </div>
        <span class="form-group bmd-form-group">
            <input type="submit" class="btn btn-raised btn-info" value="Search">

        </form:form>
        <c:if test="${reset==true}">
            <a href="/"> <button class="btn btn-raised btn-danger" value="Clear">Clear</button></a>
        </c:if>
        </span>

</nav>
<div class="container-fluid">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center"><span class="lead"><b>List of Users </b></span></div>


<table class="table table-hover">

    <thead>
        <tr>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Country</th>
            <th>Birth Date</th>
            <th>Marital Status</th>
            <th>Skills</th>
            <th>Operations</th>
        </tr>
    </thead>
    <tbody>

        <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.firstname}</td>
            <td>${user.lastname}</td>
            <td>${user.country}</td>
            <c:set var = "userDate" value = "${user.birthDate}" />
            <td><fmt:formatDate type = "date"
                                value = "${userDate}" /></td>
            <td>${user.maritalStatus.maritalname}</td>
            <td>
            <c:forEach items="${user.skills}" var="skills">

                ${skills.skillname}

            </c:forEach>
            </td>
            <td><a href="<c:url value="edit-user-${user.id}"/>" class="btn btn-raised btn-warning">Edit</a>
            <button type="button" class="btn btn-raised btn-danger" onclick="confirmDelete(${user.id})" id="deleteButton">Delete</button></td>
        </tr>
        </c:forEach>

</table>
    </div>




</div>


<script>
    function confirmDelete(userId){

        var confirmation = confirm("Are you sure?");

        if(confirmation==true){
            window.location.href = "delete-user-"+userId;
            alert("User deleted!");
        }

    }
</script>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>
<script src="https://cdn.rawgit.com/HubSpot/tether/v1.3.4/dist/js/tether.min.js"></script>
<script src="https://cdn.rawgit.com/FezVrasta/bootstrap-material-design/dist/dist/bootstrap-material-design.iife.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="https://maxcdn.bootstrapcdn.com/js/ie10-viewport-bug-workaround.js"></script>
<script>
    $('body').bootstrapMaterialDesign();
</script>
</body>
</html>