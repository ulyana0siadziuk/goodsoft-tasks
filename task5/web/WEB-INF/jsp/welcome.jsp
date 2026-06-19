<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:myhtml title="Главная">
    <h1>Главная страница</h1>
    <p>Добро пожаловать в систему!</p>

    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <p>
            <a href="${pageContext.request.contextPath}/loginedit.jhtml">Сменить пароль</a>
        </p>
    </c:if>
</t:myhtml>