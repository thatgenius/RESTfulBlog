<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url value="/createComment" var="createCommentURL"/>
<html>
<head>
    <title></title>
</head>
<body>

<jsp:include page="header.jsp"/>

<c:choose>
    <c:when test="${post == null}">Post doesn't exist</c:when>
    <c:otherwise>
<table width="100%">
<tr>
    <td width="8%"></td>
    <td width="84%">
        <table border="1" cellpadding="10px" width="100%" style="border-collapse: collapse">
            <tr>
                    <td>${post.getTitle()}</td>
            </tr>
            <tr>
                    <td>${post.getContent()}</td>
            </tr>

        </table>
        <br/>

        <sf:form method="post" modelAttribute="comment" action="${createCommentURL}">
            <sf:input type="hidden" path="post_id" value="${post.id}" />
            <sf:input type="hidden" path="level" value="1" />
            <sf:textarea path="content" /><br/><br/>
            <button type="submit">Post</button>
        </sf:form>


        <c:forEach items="${comments}" var="comment">

        <table border="0">
            <tr>
                <td style="width: 20px"></td>
                <td>
                    <table border="0">
                        <c:if test="${comment.level == 1}">
                            <tr>
                                <td colspan="2">
                                        <small>${dateFormatter.format(comment.time_created)}</small><br/>
                                        ${comment.content}
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <div onclick="return toggleMe('comment ${comment.id}')"><div style="color: #2e9fff; font-weight: 700;">Reply</div></div>
                                    <div id="comment ${comment.id}" style="display: none;">
                                        <sf:form method="post" modelAttribute="comment" action="${createCommentURL}">
                                            <sf:input type="hidden" path="post_id" value="${post.id}" />
                                            <sf:input type="hidden" path="level" value="2" />
                                            <sf:input type="hidden" path="parent_id" value="${comment.id}" />
                                            <sf:textarea path="content" /><br/><br/>
                                            <button type="submit">Post</button>
                                        </sf:form>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${comment.level == 2}">
                                <tr>
                                    <td style="width: 40px">
                                    </td>

                                    <td>
                                        <small>${dateFormatter.format(comment.time_created)}</small><br/>
                                        ${comment.content}
                                    </td>
                                </tr>
                        </c:if>
                    </table>
                </td>
            </tr>
        </table>
        </c:forEach>

    </td>
    <td width="8%"></td>
</tr>
</table>

    </c:otherwise>

</c:choose>

<script type="text/javascript">
    function toggleMe(a){
        var e=document.getElementById(a);
        if(!e)return true;
        if(e.style.display=="none"){
            e.style.display="block"
        }
        else{
            e.style.display="none"
        }
        return true;
    }
</script>

</body>
</html>