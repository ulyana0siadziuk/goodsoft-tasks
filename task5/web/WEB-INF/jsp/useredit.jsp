<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:choose>
    <c:when test="${editMode}">
        <spring:message code="useredit.title.edit" var="pageTitle"/>
    </c:when>
    <c:otherwise>
        <spring:message code="useredit.title.add" var="pageTitle"/>
    </c:otherwise>
</c:choose>

<t:myhtml title="${pageTitle}">
    <h1>${pageTitle}</h1>

    <form:form modelAttribute="editUser"
               action="${pageContext.request.contextPath}/useredit"
               method="post">

        <c:if test="${editMode}">
            <form:hidden path="oldLogin"/>
        </c:if>

        <label><spring:message code="useredit.label.login"/>:</label><br>
        <form:input path="login" readonly="${editMode}"/><br>
        <form:errors path="login" cssClass="error" element="p"/><br>

        <label><spring:message code="useredit.label.password"/>:</label><br>
        <c:if test="${editMode}">
            <p class="form-hint"><spring:message code="useredit.password.hint"/></p>
        </c:if>
        <form:password path="password"/><br>
        <form:errors path="password" cssClass="error" element="p"/><br>

        <label><spring:message code="useredit.label.name"/>:</label><br>
        <form:input path="name"/><br>
        <form:errors path="name" cssClass="error" element="p"/><br>

        <label><spring:message code="useredit.label.birthday"/>:</label><br>
        <form:input path="birthday" type="date"/><br>
        <form:errors path="birthday" cssClass="error" element="p"/><br>

        <label><spring:message code="useredit.label.age"/>:</label><br>
        <form:input path="age" type="number" min="18"/><br>
        <form:errors path="age" cssClass="error" element="p"/><br>

        <label><spring:message code="useredit.label.salary"/>:</label><br>
        <form:input path="salary" type="number" min="0"/><br>
        <form:errors path="salary" cssClass="error" element="p"/><br>

        <label><spring:message code="useredit.label.roles"/>:</label><br>
        <form:select path="roles" items="${allRoles}" multiple="true" size="7"
                     cssClass="roles-select"/><br>
        <form:errors path="roles" cssClass="error" element="p"/><br>

        <button type="submit"><spring:message code="useredit.submit"/></button>
        <a href="${pageContext.request.contextPath}/users">
            <spring:message code="useredit.cancel"/>
        </a>
    </form:form>
</t:myhtml>