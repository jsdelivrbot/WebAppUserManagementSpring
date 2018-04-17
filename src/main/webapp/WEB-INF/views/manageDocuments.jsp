<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Upload/Download/Delete Documents</title>
    <!-- Material Design fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- Bootstrap Material Design -->
    <link rel="stylesheet" href="https://cdn.rawgit.com/FezVrasta/bootstrap-material-design/dist/dist/bootstrap-material-design.min.css">

</head>

<body>

<div class="container-fluid">


        <div class="panel-heading"><span class="lead">Upload New Document</span></div>
        <div class="uploadcontainer">
            <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">

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