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
        <c:if test="${not empty errors.login}">
            <p class="error">${errors.login}</p>
        </c:if>
        <br>

        <label>Пароль:</label><br>
        <form:password path="password"/><br>
        <c:if test="${not empty errors.password}">
            <p class="error">${errors.password}</p>
        </c:if>
        <br>

        <label>Имя:</label><br>
        <form:input path="name"/><br>
        <c:if test="${not empty errors.name}">
            <p class="error">${errors.name}</p>
        </c:if>
        <br>

        <label>Дата рождения:</label><br>
        <form:input path="birthday" type="date"/><br>
        <c:if test="${not empty errors.birthday}">
            <p class="error">${errors.birthday}</p>
        </c:if>
        <br>

        <label>Возраст:</label><br>
        <form:input path="age" type="number" min="0"/><br>
        <c:if test="${not empty errors.age}">
            <p class="error">${errors.age}</p>
        </c:if>
        <br>

        <label>Зарплата:</label><br>
        <form:input path="salary" type="number" min="0"/><br>
        <c:if test="${not empty errors.salary}">
            <p class="error">${errors.salary}</p>
        </c:if>
        <br>

        <label>Роли (удерживайте Ctrl для выбора нескольких):</label><br>
        <form:select path="roles" items="${allRoles}" multiple="true" size="7"
                     cssStyle="min-width: 220px;"/><br>
        <c:if test="${not empty errors.roles}">
            <p class="error">${errors.roles}</p>
        </c:if>
        <br>

        <button type="submit">Сохранить</button>
        <a href="${pageContext.request.contextPath}/users">Отмена</a>
    </form:form>
</t:myhtml>