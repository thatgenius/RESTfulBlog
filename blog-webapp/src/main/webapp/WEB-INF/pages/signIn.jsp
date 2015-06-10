<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Login Page</title></head><body onload='document.f.j_username.focus();'>

<jsp:include page="header.jsp"/>
<c:url value="/static/j_spring_security_check" var="signInNativeProcessingURL"/>
<c:url value="/signInProcessing" var="signInNativeProcessingURL2"/>
<c:url value="/signUp" var="signUpURL"/>

<c:if test="${not empty param.login_error}">
  <div class="error">
    Your login attempt was not successful, try again.<br />
    Reason: <span style="color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</span>
  </div>
</c:if>

<span style="font-size: 1.51em">Sign in with login and password</span>
<span style="font-size: 1.47em; color: silver">or <a style="color: silver" href="${signUpURL}">sign up</a></span>

<form name='f' action='${signInNativeProcessingURL}' method='POST'>
  <table>
    <tr><td>Login:</td><td><input type='text' name='j_username' value=''></td></tr>
    <tr><td>Password:</td><td><input type='password' name='j_password'/></td></tr>
    <tr><td colspan='2'><input name="submit" type="submit" value="Sign in"/></td></tr>
  </table>
</form>


</body></html>
