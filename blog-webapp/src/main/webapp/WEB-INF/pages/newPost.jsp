
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url value="/createPost" var="createPostURL"/>
<html>
<head>
    <title></title>
</head>
<body>

<jsp:include page="header.jsp"/>

<sf:form method="post" modelAttribute="post" action="${createPostURL}">
    <sf:input path="title" /><br/><br/>
    <sf:textarea path="content" /><br/><br/>
    <button type="submit">Save</button>
</sf:form>




</body>
</html>
