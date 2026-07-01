<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<spring:message code="users.title" var="pageTitle"/>

<t:myhtml title="${pageTitle}">
    <h1><spring:message code="users.heading"/></h1>

    <form:form modelAttribute="deleteUserForm"
               action="${pageContext.request.contextPath}/users"
               method="post">
        <form:errors cssClass="error" element="p"/>
    </form:form>

    <p>
        <a class="btn" href="${pageContext.request.contextPath}/useredit">
            <spring:message code="users.add"/>
        </a>
    </p>

    <table class="users-table">
        <thead>
        <tr>
            <th><spring:message code="users.col.login"/></th>
            <th><spring:message code="users.col.name"/></th>
            <th><spring:message code="users.col.age"/></th>
            <th><spring:message code="users.col.salary"/></th>
            <th><spring:message code="users.col.roles"/></th>
            <th><spring:message code="users.col.birthday"/></th>
            <th><spring:message code="users.col.actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td><c:out value="${u.login}"/></td>
                <td><c:out value="${u.name}"/></td>
                <td><c:out value="${u.age}"/></td>
                <td><c:out value="${u.salary}"/></td>
                <td><c:out value="${u.rolesAsString}"/></td>
                <td>
                    <fmt:formatDate value="${u.birthdayDate}" pattern="dd.MM.yyyy"/>
                </td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/useredit?login=${u.login}">
                        <spring:message code="users.edit"/>
                    </a>

                    <spring:message code="users.delete.confirm"
                                    arguments="${u.login}"
                                    var="deleteConfirm"
                                    javaScriptEscape="true"/>

                    <form:form modelAttribute="deleteUserForm"
                               action="${pageContext.request.contextPath}/users"
                               method="post"
                               cssClass="inline-form">
                        <form:hidden path="login" value="${u.login}"/>
                        <button type="submit"
                                onclick="return confirm('${deleteConfirm}');">
                            <spring:message code="users.delete"/>
                        </button>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</t:myhtml>