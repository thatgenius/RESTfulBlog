<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body bgcolor="#ffe4c4">

<jsp:include page="header.jsp"/>
<c:url value="/update" var="updateURL"/>
<c:url value="/deletePost" var="deletePostURL"/>


<c:choose>
    <c:when test="${posts == null || fn:length(posts) == 0}">You haven't posted anything yet</c:when>
    <c:otherwise>
        <table width="100%" border="0">
            <tr>
                <td width="8%"></td>
                <td width="84%">

        <table bgcolor="white" border="0" cellpadding="10px" width="100%" style="border-collapse: collapse">
            <c:forEach items="${posts}" var="c">
                <table bgcolor="white" style="border: 0px solid black" width="84%" border="0">
                <tr>
                    <td>
                        <a href="post/${c.id}">${c.getTitle()}</a>
                        <security:authorize access="hasRole('ROLE_ADMIN')">
                        <a href="#" id="deletePost" class="${c.id}">Delete</a>
                        <a href="${updateURL}/${c.id}">Update</a>
                        </security:authorize>
                        ${dateFormatter.format(c.timeCreated)}
                    </td>
                </tr>

                <tr>
                    <td width="100px">
                        <img width="100px" height="80px" src="http://svetlyi-mir.com/forum/uploads/attachment/2014-02/1392145420_tigr-plyvet.jpg">
                    </td>
                    <td valign="top">
                        <c:choose>
                            <c:when test="${c.getContent().length() > 100}">
                                ${c.getContent().substring(0, 100)} ...
                            </c:when>
                            <c:otherwise>
                                ${c.getContent()}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                </table>
                <br/>


            </c:forEach>

        </table>

        </td>
        <td width="8%"></td>
        </tr>
        </table>
    </c:otherwise>

</c:choose>



<%--ajax request to delete post--%>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function deletePost(id, curElement) {
        $.ajax({
            data: {id: id},
            url: '${deletePostURL}/' + id,
            type: 'GET',
            success: function (data) {
                curElement.parentNode.parentNode.parentNode.parentNode.nextElementSibling.remove();
                curElement.parentNode.parentNode.parentNode.parentNode.remove();
            }
        });
        return false;
    }
    $("a#deletePost").click(function () {
        return deletePost(this.getAttribute("class"), this);
    });
</script>


</body>
</html>
