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

    <!-- INCLUDE NAVBAR -->
    <%@include file="_navbar.jsp"%>

<!-- NOTIFICATIONS EDIT, SUCCESS, SAVE -->
<c:if test="${successMessage==true}">


    <script>
        createNotification("${notificationsSuccess.type}","${notificationsSuccess.message}","${notificationsSuccess.timer}",0)
    </script>
</c:if>
<!-- NOTIFICATIONS NO RESULTS FOR SEARCH -->
<c:if test="${resultSize==0}">

    <c:set value="false" var="control"></c:set>
    <script>
        createNotification("${notifications.type}","${notifications.message}","${notifications.timer}",0)
    </script>

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
                            <a class="dropdown-item" style="color:black" data-toggle="modal" data-target="#exampleModal${docList.id}">Info</a>
                            <a href="<c:url value="download-document-${docList.id}"/>" class="dropdown-item" style="color:blue">Download File</a>
                        </div>
                    </div>
                    <!-- Modal File Infos -->
                    <div class="modal fade" id="exampleModal${docList.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel" align="center">File Infos</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <table align="center">
                                        <thead>
                                            <tr>
                                                <td><b>Filename</b></td>
                                                <td><b>Type</b></td>
                                                <td><b>Description</b></td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>${docList.name}</td>
                                                <td>${docList.type}</td>
                                                <td>${docList.description}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>

                            </div>
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
    $("#error-alert0").fadeTo(3000, 500).slideUp(500);
</script>


</body>
</html>
