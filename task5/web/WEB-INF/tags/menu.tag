<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="site-menu">
    <div class="container menu-row">
        <a href="${pageContext.request.contextPath}/welcome">
            <spring:message code="menu.home"/>
        </a>

        <c:if test="${sessionScope.user.admin}">
            <a href="${pageContext.request.contextPath}/users">
                <spring:message code="menu.users"/>
            </a>
        </c:if>
    </div>
</nav>