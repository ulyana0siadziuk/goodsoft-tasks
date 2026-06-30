<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="site-menu">
    <div class="container menu-row">
        <a href="${pageContext.request.contextPath}/welcome">Главная</a>

        <c:if test="${sessionScope.user.admin}">
            <a href="${pageContext.request.contextPath}/users">Пользователи</a>
        </c:if>
    </div>
</nav>