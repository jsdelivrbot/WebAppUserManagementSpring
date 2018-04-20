<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Registration Form</title>
    <%@include file="_links.jsp"%>

</head>
<body>
    <!-- INCLUDE NAVBAR -->
    <%@include file="_navbar.jsp"%>

<div class="container-fluid">


    <div class="well lead"><b>User Registration Form</b></div>
    <form:form modelAttribute="user" method="POST" class="form-horizontal" id="formRegistration">
        <form:input path="id" id="id" type="hidden"></form:input>

        <!-- NOTIFICATIONS LIST -->

        <c:if test="${errorNotification==true}">
            <c:set value="false" var="control"></c:set>
            <c:set var="count" value="0"></c:set>

        <!-- CHIAMO FUNZIONE JAVASCRIPT NELL'HEADER -->
            <c:forEach items="${notificationsList}" var="notificationsList">
                <script>
                    createNotification("${notificationsList.type}","${notificationsList.message}","${notificationsList.timer}","${count}")
                </script>
                <c:set var="count" value="${count+1}"></c:set>

            </c:forEach>
        </c:if>


    <!-- FORMS -->
            <div class="form-group ${errorFirst}">
                <label>Firstname*</label>
                    <form:input path="firstname" type="text" cssClass="form-control" cssErrorClass="form-control form-control-danger"></form:input>
                <div class="form-group has-danger">
                    <form:errors path="firstname" cssClass="form-control-feedback" />
                </div>
            </div>

    <div class="form-group ${errorLast}">
        <label>Lastname*</label>
            <form:input path="lastname" type="text" cssClass="form-control"></form:input>
        <div class="form-group has-danger">
            <form:errors path="lastname" class="form-control-feedback"/>
        </div>
            </div>

    <div class="form-group">
        <label>Country</label>
            <form:input path="country" type="text" cssClass="form-control"></form:input>
        <div class="form-group has-danger">
            <form:errors path="country" class="form-control-feedback"/>
        </div>
            </div>


    <div class="form-group ${errorBirthDate}">
        <label>Birth Date*</label>
                <form:input path="birthDate" type="date" cssClass="form-control"></form:input>
        <div class="form-group has-danger">
            <form:errors path="birthDate" class="form-control-feedback"/>
        </div>
            </div>


    <div class="form-group">
        <label>Marital Status</label>
                    <form:select path="maritalStatus.id" cssClass="form-control">
                        <form:options items="${maritalStatus}" itemLabel="maritalname" itemValue="id" ></form:options>
                    </form:select>
        <div class="form-group has-danger">
            <form:errors path="maritalStatus" class="form-control-feedback"/>
        </div>
                </div>



    <div class="form-group">
        <label >Skills</label>
                    <select onchange="addSkill(this)" id="updateSkill" class="form-control">
                        <c:forEach items="${skills}" var="resultSkills">
                            <c:set var="disabled"  value=""></c:set>

                            <c:forEach items="${user.skills}" var="userSkills">
                                <c:if test="${userSkills.id == resultSkills.id}">
                                    <c:set var="disabled" value="disabled"></c:set>
                                </c:if>
                            </c:forEach>

                            <option value="${resultSkills.id}" ${disabled} >${resultSkills.skillname}</option>
                        </c:forEach>
                    </select>

        <div class="form-group has-danger">
            <form:errors path="skills" class="form-control-feedback"/>
        </div>
                </div>



        <div class="form-group" id="myUpdatedDiv" style="background-color:lavender;">
            <div class="btn-group" id="buttonGroup">
                <c:forEach items="${user.skills}" var="listEdit">
                    <input type="hidden" value="${listEdit.id}"  id="idskillUp${listEdit.id}" name="skills"/>
                    <input type="button" class="btn btn-outline-dark" value="${listEdit.skillname}" id="${listEdit.id}" onclick="removeSkill(${listEdit.id})"/>
                </c:forEach>
            </div>
        </div>

        <c:choose>
            <c:when test="${edit==false}">
                <input type="submit" value="Register" class="btn btn-raised btn-success"/>
                or <a href="<c:url value="/" />" class="alert-link">Back</a>
            </c:when>
            <c:otherwise>
                <input type="submit" value="Update" class="btn btn-raised btn-success"/>
                or <a href="<c:url value="/" />" class="alert-link">Back</a>
            </c:otherwise>
        </c:choose>
        <div>
            * Fields are mandatory
        </div>
    </form:form>

</div>

<script>
    function addSkill(myNewThis){
        //prendo identificativo della skill (value)
        var skill = myNewThis.options[myNewThis.selectedIndex].value;
        //prendo nome della skill (text)
        var skillName = myNewThis.options[myNewThis.selectedIndex].text;
        console.log("skill: "+skill);
        console.log("skillName: "+skillName);

        //setto la relativa opzione a disabilitata = true
        myNewThis.options[myNewThis.selectedIndex].disabled = true;


        //creo sul div l'input
        var inputSkill = document.createElement("input");
        inputSkill.type = "hidden";
        inputSkill.value = skill;
        inputSkill.id = "idskillUp"+skill;
        inputSkill.name = "skills";

        //creo sul div il button
        var btn = document.createElement("input");
        btn.type="button";
        btn.value = skillName;
        btn.id=skill;
        btn.className="btn btn-outline-dark";

        btn.onclick = function () {
            console.log("document element skill (INPUT BUTTON)");
            console.log(document.getElementById(skill));
            document.getElementById(skill).remove();

            console.log("document element idskillup (INPUT HIDDEN)");
            console.log(document.getElementById("idskillUp"+skill));
            document.getElementById("idskillUp"+skill).remove();

            myNewThis.options[skill-1].disabled = false;
            console.log("OPTIONS:"+myNewThis.options[skill-1].value);

        }
        document.getElementById("buttonGroup").appendChild(inputSkill);
        document.getElementById("buttonGroup").appendChild(btn);
    }


    function removeSkill(skillId) {
        var myLastSkill = document.getElementById(skillId).id;
        console.log("myLastSkill: "+ document.getElementById(skillId).id);
        console.log("document.getElementById(skillID)");
        console.log(document.getElementById(skillId));

        //Rimuovo bottone
        var btnSkill = document.getElementById(skillId);
        btnSkill.remove();

        //Rimuovo input
        var inputUpdatedSkill = document.getElementById("idskillUp"+skillId);
        inputUpdatedSkill.remove();

        console.log("Selection");
        console.log(document.getElementById("updateSkill"));
        console.log("Single element");
        console.log(document.getElementById("updateSkill").options[myLastSkill-1]);
        document.getElementById("updateSkill").options[myLastSkill-1].disabled = false;


    }


</script>

<!-- INCLUDE SCRIPTS -->
<%@include file="_scriptsFooter.jsp"%>


</body>
</html>
