<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Смена пароля</title>
</head>
<body>
<h1>Смена пароля</h1>

<c:if test="${not empty errorMessage}">
    <p style="color:red">${errorMessage}</p>
</c:if>

<form action="${pageContext.request.contextPath}/loginedit.jhtml" method="post">
    <label>Старый пароль: <input type="password" name="oldPassword" required></label><br><br>
    <label>Новый пароль: <input type="password" name="newPassword" required></label><br><br>
    <input type="hidden" name="action" value="changePassword">
    <button type="submit">Сохранить</button>
</form>

<p><a href="${pageContext.request.contextPath}/welcome.jhtml">Назад</a></p>
</body>
</html>
