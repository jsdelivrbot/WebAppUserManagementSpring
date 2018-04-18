<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
   <%@include file="_links.jsp"%>
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
            <input type="submit" class="btn btn-outline-info" value="Search">

        </form:form>
        <c:if test="${reset==true}">
            <a href="/"> <button class="btn btn-outline-danger" value="Clear" name="clearBtn" id="clearBtn" onclick="hideClear()">Clear</button></a>
        </c:if>
        </span>


</nav>
<!-- NOTIFICATIONS EDIT, SUCCESS, SAVE -->
<c:if test="${successMessage==true}">

    <div class="alert alert-success alert-dismissible fade show" role="alert" id="success-alert" name="success-alert">
        <strong>${notificationsSuccess.type}!</strong> ${notificationsSuccess.message}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>

</c:if>
<!-- NOTIFICATIONS NO RESULTS FOR SEARCH -->
<c:if test="${resultSize==0}">

    <div class="alert alert-warning" role="alert">
        <strong>We're sorry!</strong> No results found.
    </div>

</c:if>
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
            <th>File</th>
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
            <button type="button" class="btn btn-raised btn-danger" onclick="confirmDelete(${user.id})" id="deleteButton">Delete</button>
            </td>
            <td>
                <c:if test="${user.userDocuments.isEmpty()}">
                    <a href="<c:url value="add-document-${user.id}"/>" class="btn btn-raised btn-info">Upload</a>
                </c:if>

                <c:forEach items="${user.userDocuments}" var="docList">
                    <div class="btn-group">
                        <button class="btn dropdown-toggle" type="button" id="buttonMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                ${docList.name}
                        </button>
                        <div class="dropdown-menu" aria-labelledby="buttonMenu1">
                            <a href="<c:url value="edit-document-${docList.id}"/>" class="dropdown-item" style="color:orangered">Edit</a>
                            <a class="dropdown-item" onclick="confirmDocumentDelete(${docList.id})" style="color:red">Delete</a>
                            <a href="<c:url value="download-document-${docList.id}"/>" class="dropdown-item" style="color:blue">Download File</a>
                        </div>
                    </div>


                </c:forEach>

            </td>
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

        }

    }

    function confirmDocumentDelete(docId){
        var confirmation = confirm("Are you sure to delete document?");

        if(confirmation==true){
            window.location.href = "delete-document-"+docId;

        }
    }



</script>

<!-- INCLUDE SCRIPTS -->
<%@include file="_scriptsFooter.jsp"%>

<!-- FOR ALERTS -->
<!-- fadeTo dice dopo quanto deve andare via e in che opacità, mentre slideUp definisce la velocità di sliding -->
<script>
    $("#success-alert").fadeTo(3000, 500).slideUp(500);
</script>
<script>
    function hideClear(){
        var btnClear = document.getElementById("clearBtn");
        btnClear.remove();
    }
</script>
</body>
</html>
