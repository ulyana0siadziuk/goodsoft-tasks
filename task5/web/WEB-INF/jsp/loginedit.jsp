<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Смена пароля</title>
</head>
<body>
<h1>Смена пароля</h1>

<form:form modelAttribute="changePasswordForm" action="${pageContext.request.contextPath}/loginedit" method="post">
    <form:errors cssClass="error" element="p" cssStyle="color:red"/>

    <label>Старый пароль: <form:password path="oldPassword" required="required"/></label><br><br>
    <label>Новый пароль: <form:password path="newPassword" required="required"/></label><br><br>
    <button type="submit">Сохранить</button>
</form:form>

<p><a href="${pageContext.request.contextPath}/welcome">Назад</a></p>
</body>
</html>