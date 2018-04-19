<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Upload/Download/Delete Documents</title>
    <%@include file="_links.jsp"%>
</head>

<body>
    <!-- INCLUDE NAVBAR -->
    <%@include file="_navbar.jsp"%>

<div class="container-fluid">


        <div class="panel-heading"><span class="lead">Upload New Document</span></div>
        <div class="uploadcontainer">
            <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">

                <c:if test="${errorFile}" var="errorFile">
                    <div class="alert alert-${notifications.type} alert-dismissible fade show" role="alert" id="error-alert">
                        ${notifications.message}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <div class="form-group ${error}">
                    <label>Upload a document</label>
                            <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                            <div class="form-group has-danger">
                                <form:errors path="file" class="form-control-feedback"/>
                            </div>
                        </div>


                <div class="form-group">
                    <label>Description</label>
                            <form:input type="text" path="description" id="description" cssClass="form-control"/>
                        </div>


                <div class="row">
                    <div class="form-actions floatRight">
                        <c:choose>
                            <c:when test="${editDoc==false}">
                                <input type="submit" value="Upload" class="btn btn-raised btn-success">
                            </c:when>
                            <c:otherwise>
                                <input type="submit" value="Modify" class="btn btn-raised btn-success">
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>

            </form:form>
    </div>
    <div class="well">
        Go to <a href="<c:url value='/' />">Users List</a>
    </div>
</div>

<!-- INCLUDE SCRIPTS -->
<%@include file="_scriptsFooter.jsp"%>

<!-- FOR ALERTS -->
<!-- fadeTo dice dopo quanto deve andare via e in che opacità, mentre slideUp definisce la velocità di sliding -->
<script>
    $("#error-alert").fadeTo(3000, 500).slideUp(500);
</script>
</body>
</html>