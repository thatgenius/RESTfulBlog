<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>

</head>
<body>

<jsp:include page="header.jsp"/>
<c:url value="/signUp" var="signUpURL"/>
<c:url value="/signIn" var="signInURL"/>

<span style="font-size: 2.17em">Sign up</span> <span style="font-size: 1.17em">or <a href="${signInURL}">sign in</a> if already registered</span>
<br/>

	<%--<sf:form method="post" modelAttribute="user" action="http://localhost:8081/signUp/">
		Login <sf:input path="username" /><br/><br/>
		Password <sf:input path="password" /><br/><br/>
		<button type="submit">Sign up! =)</button>
	</sf:form>--%>

<sf:form method="post" modelAttribute="user" action="${signUpURL}">
<table>
	<tr><td>Login:</td><td><sf:input path="username" /></td></tr>
	<tr><td>Password:</td><td><sf:input path="password" /></td></tr>
	<tr><td colspan='2'><button type="submit">Sign up! =)</button></td></tr>
</table>
</sf:form>

</body>
</html>