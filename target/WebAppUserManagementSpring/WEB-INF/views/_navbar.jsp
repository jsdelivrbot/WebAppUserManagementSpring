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