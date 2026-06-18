<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Пользователи</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<main class="page-content">
    <div class="container">
        <h1>Список пользователей</h1>

        <p>
            <a class="btn" href="${pageContext.request.contextPath}/useredit.jhtml">Добавить</a>
        </p>

        <table class="users-table">
            <thead>
            <tr>
                <th>Логин</th>
                <th>ФИО</th>
                <th>Email</th>
                <th>Роль</th>
                <th>Дата рождения</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td><c:out value="${u.login}"/></td>
                    <td><c:out value="${u.fullName}"/></td>
                    <td><c:out value="${u.email}"/></td>
                    <td><c:out value="${u.role}"/></td>
                    <td>
                        <fmt:formatDate value="${u.birthdayDate}" pattern="dd.MM.yyyy"/>
                    </td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/useredit.jhtml?login=${u.login}">
                            Редактировать
                        </a>

                        <form method="post"
                              action="${pageContext.request.contextPath}/users.jhtml"
                              style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="login" value="${u.login}">
                            <button type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>

<%@ include file="footer.jsp" %>

</body>
</html>