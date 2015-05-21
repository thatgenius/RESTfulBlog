<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<jsp:include page="header.jsp"/>
<c:url value="/update" var="updateURL"/>

<sf:form method="post" modelAttribute="post" action="${updateURL}">
    <sf:hidden path="id"/>
    <sf:input path="title" /><br/><br/>
    <sf:textarea path="content" /><br/><br/>
    <button type="submit">Save</button>
</sf:form>




</body>
</html>
