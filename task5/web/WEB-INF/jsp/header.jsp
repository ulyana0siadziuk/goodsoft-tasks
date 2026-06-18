<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="site-header">
  <div class="container header-row">
    <div class="logo-box">LOGO</div>

    <div class="header-user">
            <span>
                Привет,
                <strong>
                    <c:out value="${sessionScope.userInfo.fullName}" />
                </strong>
            </span>
      <a href="${pageContext.request.contextPath}/logout.jhtml">Выйти</a>
    </div>
  </div>
</header>