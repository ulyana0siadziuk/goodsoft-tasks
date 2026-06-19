<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="site-menu">
  <div class="container menu-row">
    <a href="${pageContext.request.contextPath}/welcome.jhtml">Главная</a>

    <c:if test="${sessionScope.user.role == 'ADMIN'}">
      <a href="${pageContext.request.contextPath}/users.jhtml">Пользователи</a>
    </c:if>
  </div>
</nav>