<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="site-header">
    <div class="container header-row">
        <div class="logo-box">LOGO</div>

        <div class="header-user">
            <span>
                Привет,
                <strong>
                    <c:out value="${sessionScope.user.name}"/>
                </strong>
            </span>
            <a href="${pageContext.request.contextPath}/logout.jhtml">Выйти</a>
        </div>
    </div>
</header>