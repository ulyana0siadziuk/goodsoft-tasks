<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
</head>
<body>
<h1>Авторизация</h1>

<form:form modelAttribute="loginForm" action="${pageContext.request.contextPath}/login" method="post">
    <form:errors cssClass="error" element="p" cssStyle="color:red"/>

    <label>Логин: <form:input path="login" required="required"/></label><br><br>
    <label>Пароль: <form:password path="password" required="required"/></label><br><br>
    <button type="submit">Войти</button>
</form:form>
</body>
</html>