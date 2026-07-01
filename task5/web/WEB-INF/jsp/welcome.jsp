<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<spring:message code="welcome.title" var="pageTitle"/>

<t:myhtml title="${pageTitle}">
    <h1><spring:message code="welcome.heading"/></h1>
    <p><spring:message code="welcome.text"/></p>

    <sec:authorize access="authentication.principal.admin">
        <p>
            <a href="${pageContext.request.contextPath}/loginedit">
                <spring:message code="welcome.changePassword"/>
            </a>
        </p>
    </sec:authorize>
</t:myhtml>