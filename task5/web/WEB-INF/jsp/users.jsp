<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:myhtml title="Пользователи">
    <h1>Список пользователей</h1>

    <form:form modelAttribute="deleteUserForm"
               action="${pageContext.request.contextPath}/users"
               method="post">
        <form:errors cssClass="error" element="p"/>
    </form:form>

    <p>
        <a class="btn" href="${pageContext.request.contextPath}/useredit">Добавить</a>
    </p>

    <table class="users-table">
        <thead>
        <tr>
            <th>Логин</th>
            <th>Имя</th>
            <th>Возраст</th>
            <th>Зарплата</th>
            <th>Роли</th>
            <th>Дата рождения</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td><c:out value="${u.login}"/></td>
                <td><c:out value="${u.name}"/></td>
                <td><c:out value="${u.age}"/></td>
                <td><c:out value="${u.salary}"/></td>
                <td><c:out value="${u.rolesAsString}"/></td>
                <td>
                    <fmt:formatDate value="${u.birthdayDate}" pattern="dd.MM.yyyy"/>
                </td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/useredit?login=${u.login}">
                        Редактировать
                    </a>

                    <form:form modelAttribute="deleteUserForm"
                               action="${pageContext.request.contextPath}/users"
                               method="post"
                               cssStyle="display:inline;">
                        <form:hidden path="login" value="${u.login}"/>
                        <button type="submit"
                                onclick="return confirm('Удалить пользователя ${u.login}?');">
                            Удалить
                        </button>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</t:myhtml>