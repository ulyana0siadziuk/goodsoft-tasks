<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="${editMode ? 'Редактирование пользователя' : 'Добавление пользователя'}"/>

<t:myhtml title="${pageTitle}">
    <h1>${pageTitle}</h1>

    <form:form modelAttribute="editUser"
               action="${pageContext.request.contextPath}/useredit"
               method="post">

        <c:if test="${editMode}">
            <input type="hidden" name="oldLogin" value="${editUser.login}">
        </c:if>

        <label>Логин:</label><br>
        <form:input path="login" readonly="${editMode}"/><br>
        <form:errors path="login" cssClass="error" element="p"/><br>

        <label>Пароль:</label><br>
        <form:password path="password"/><br>
        <form:errors path="password" cssClass="error" element="p"/><br>

        <label>Имя:</label><br>
        <form:input path="name"/><br>
        <form:errors path="name" cssClass="error" element="p"/><br>

        <label>Дата рождения:</label><br>
        <form:input path="birthday" type="date"/><br>
        <form:errors path="birthday" cssClass="error" element="p"/><br>

        <label>Возраст:</label><br>
        <form:input path="age" type="number" min="0"/><br>
        <form:errors path="age" cssClass="error" element="p"/><br>

        <label>Зарплата:</label><br>
        <form:input path="salary" type="number" min="0"/><br>
        <form:errors path="salary" cssClass="error" element="p"/><br>

        <label>Роли (удерживайте Ctrl для выбора нескольких):</label><br>
        <form:select path="roles" items="${allRoles}" multiple="true" size="7"
                     cssStyle="min-width: 220px;"/><br>
        <form:errors path="roles" cssClass="error" element="p"/><br>

        <button type="submit">Сохранить</button>
        <a href="${pageContext.request.contextPath}/users">Отмена</a>
    </form:form>
</t:myhtml>