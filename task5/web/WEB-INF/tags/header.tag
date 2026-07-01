<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<header class="site-header">
    <div class="container header-row">
        <div class="logo-box">LOGO</div>

        <div class="header-user">
            <div class="lang-switcher">
                <a href="?lang=ru" title="Русский">RU</a>
                <a href="?lang=en" title="English">EN</a>
            </div>

            <span>
                <spring:message code="header.hello"/>
                <strong>
                    <c:out value="${sessionScope.user.name}"/>
                </strong>
            </span>
            <a href="${pageContext.request.contextPath}/logout">
                <spring:message code="header.logout"/>
            </a>
        </div>
    </div>
</header>