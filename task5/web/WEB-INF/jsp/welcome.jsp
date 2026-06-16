<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
</head>
<body>
<h1>Добро пожаловать!</h1>

<p>Вы вошли как: <c:out value="${sessionScope.userInfo.login}"/></p>

<p>
    <a href="${pageContext.request.contextPath}/loginedit.jhtml">Сменить пароль</a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/logout.jhtml">Выйти</a>
</p>
</body>
</html>
