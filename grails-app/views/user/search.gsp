<%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 21.02.2016
  Time: 15:25:PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Search Hubbub</title>
    <meta name  = "layout" content="main">
</head>
<body>
    <formset>
        <legend>Search for friends</legend>
        <g:form action="results">
            <label for="loginId">Login ID</label>
            <g:textField name="loginId"/>
            <g:submitButton name="search" value="Search"/>
        </g:form>
    </formset>
</body>
</html>