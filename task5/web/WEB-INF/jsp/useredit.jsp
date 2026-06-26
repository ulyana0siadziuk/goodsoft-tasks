<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="${editMode ? 'Редактирование пользователя' : 'Добавление пользователя'}"/>

<t:myhtml title="${pageTitle}">
    <h1>${pageTitle}</h1>

    <form method="post" action="${pageContext.request.contextPath}/useredit.jhtml">
        <input type="hidden" name="action" value="save">
        <c:if test="${editMode}">
            <input type="hidden" name="oldLogin" value="${editUser.login}">
        </c:if>

        <label>Логин:</label><br>
        <input type="text" name="login" required
               value="${editUser.login}" ${editMode ? 'readonly' : ''}><br>
        <c:if test="${not empty errors.login}">
            <p class="error">${errors.login}</p>
        </c:if>
        <br>

        <label>Пароль:</label><br>
        <input type="password" name="password" required
               value="${editUser.password}"><br>
        <c:if test="${not empty errors.password}">
            <p class="error">${errors.password}</p>
        </c:if>
        <br>

        <label>Имя:</label><br>
        <input type="text" name="name" required
               value="${editUser.name}"><br>
        <c:if test="${not empty errors.name}">
            <p class="error">${errors.name}</p>
        </c:if>
        <br>

        <label>Дата рождения:</label><br>
        <input type="date" name="birthday"
               value="${editUser.birthday}"><br>
        <c:if test="${not empty errors.birthday}">
            <p class="error">${errors.birthday}</p>
        </c:if>
        <br>

        <label>Возраст:</label><br>
        <input type="number" name="age" min="0"
               value="${editUser.age}"><br>
        <c:if test="${not empty errors.age}">
            <p class="error">${errors.age}</p>
        </c:if>
        <br>

        <label>Зарплата:</label><br>
        <input type="number" name="salary" min="0"
               value="${editUser.salary}"><br>
        <c:if test="${not empty errors.salary}">
            <p class="error">${errors.salary}</p>
        </c:if>
        <br>

        <label>Роли (удерживайте Ctrl для выбора нескольких):</label><br>
        <select name="roles" multiple size="7" required style="min-width: 220px;">
            <c:forEach var="roleOption" items="${allRoles}">
                <c:set var="isSelected" value="false"/>
                <c:forEach var="userRole" items="${editUser.roles}">
                    <c:if test="${userRole == roleOption}">
                        <c:set var="isSelected" value="true"/>
                    </c:if>
                </c:forEach>
                <option value="${roleOption}" <c:if test="${isSelected}">selected</c:if>>
                    <c:out value="${roleOption}"/>
                </option>
            </c:forEach>
        </select><br>
        <c:if test="${not empty errors.roles}">
            <p class="error">${errors.roles}</p>
        </c:if>
        <br>

        <button type="submit">Сохранить</button>
        <a href="${pageContext.request.contextPath}/users.jhtml">Отмена</a>
    </form>
</t:myhtml>