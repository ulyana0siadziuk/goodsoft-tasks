<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="login.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="standalone-page">

<div class="lang-switcher">
    <a href="?lang=ru">RU</a>
    <a href="?lang=en">EN</a>
</div>

<h1><spring:message code="login.heading"/></h1>

<form:form modelAttribute="loginForm"
           action="${pageContext.request.contextPath}/login"
           method="post"
           cssClass="standalone-form">
    <form:errors cssClass="error" element="p"/>

    <label><spring:message code="login.label.login"/>:
        <form:input path="login" required="required"/></label><br><br>
    <label><spring:message code="login.label.password"/>:
        <form:password path="password" required="required"/></label><br><br>
    <button type="submit"><spring:message code="login.submit"/></button>
</form:form>

</body>
</html>