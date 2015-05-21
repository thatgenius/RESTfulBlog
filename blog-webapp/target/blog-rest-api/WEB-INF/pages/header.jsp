<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/signIn" var="signIn"/>
<c:url value="/signUp" var="signUp"/>
<c:url value="/j_spring_security_logout" var="signOut"/>
<c:url value="/" var="main"/>
<c:url value="/new" var="newPost"/>

<table>
  <tr>
    <td>
    </td>
  </tr>
</table>

<security:authorize access="hasRole('ROLE_ADMIN')">
  Administrator <security:authentication property="principal.username"/>
  <a href="${signOut}">Sign out</a><br/>
  <a href="${newPost}">New post</a><br/>
</security:authorize>

<security:authorize access="hasRole('ROLE_USER')">
  <security:authentication property="principal.username"/>
  <a href="${signOut}">Sign out</a><br/>
</security:authorize>

<security:authorize access="!(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ">
  Hello guest!<br/>
  <a href="${signIn}">Sign In</a><br/>
  <a href="${signUp}">Sign Up</a><br/>
</security:authorize>


<c:if test="${not fn:endsWith(pageContext.request.requestURI, '/pages/posts.jsp')}">
<a href="${main}">Main</a><br/>
</c:if>




<br/>