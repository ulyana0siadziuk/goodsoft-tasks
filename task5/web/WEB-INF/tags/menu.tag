<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="site-menu">
    <div class="container menu-row">
        <a href="${pageContext.request.contextPath}/welcome">
            <spring:message code="menu.home"/>
        </a>

        <sec:authorize access="authentication.principal.admin">
            <a href="${pageContext.request.contextPath}/users">
                <spring:message code="menu.users"/>
            </a>
        </sec:authorize>
    </div>
</nav>