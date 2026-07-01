<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="loginedit.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="standalone-page">

<div class="lang-switcher">
    <a href="?lang=ru">RU</a>
    <a href="?lang=en">EN</a>
</div>

<h1><spring:message code="loginedit.heading"/></h1>

<form:form modelAttribute="changePasswordForm"
           action="${pageContext.request.contextPath}/loginedit"
           method="post"
           cssClass="standalone-form">
    <form:errors cssClass="error" element="p"/>

    <label><spring:message code="loginedit.oldPassword"/>:
        <form:password path="oldPassword" required="required"/></label><br><br>
    <label><spring:message code="loginedit.newPassword"/>:
        <form:password path="newPassword" required="required"/></label><br><br>
    <button type="submit"><spring:message code="loginedit.submit"/></button>
</form:form>

<p class="standalone-back">
    <a href="${pageContext.request.contextPath}/welcome">
        <spring:message code="loginedit.back"/>
    </a>
</p>

</body>
</html>