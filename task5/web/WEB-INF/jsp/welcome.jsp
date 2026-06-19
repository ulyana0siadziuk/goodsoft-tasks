<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<main class="page-content">
    <div class="container">
        <h1>Главная страница</h1>
        <p>Добро пожаловать в систему!</p>

        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <p>
                <a href="${pageContext.request.contextPath}/loginedit.jhtml">Сменить пароль</a>
            </p>
        </c:if>
    </div>
</main>

<%@ include file="footer.jsp" %>

</body>
</html>