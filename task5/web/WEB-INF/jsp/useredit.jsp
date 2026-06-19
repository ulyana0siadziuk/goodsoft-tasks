<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:myhtml title="${editMode ? 'Редактирование пользователя' : 'Добавление пользователя'}">

  <h1>
    <c:choose>
      <c:when test="${editMode}">Редактирование пользователя</c:when>
      <c:otherwise>Добавление пользователя</c:otherwise>
    </c:choose>
  </h1>

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

    <label>Email:</label><br>
    <input type="email" name="email" required
           value="${editUser.email}"><br>
    <c:if test="${not empty errors.email}">
      <p class="error">${errors.email}</p>
    </c:if>
    <br>

    <label>Фамилия:</label><br>
    <input type="text" name="surname" required
           value="${editUser.surname}"><br>
    <c:if test="${not empty errors.surname}">
      <p class="error">${errors.surname}</p>
    </c:if>
    <br>

    <label>Имя:</label><br>
    <input type="text" name="name" required
           value="${editUser.name}"><br>
    <c:if test="${not empty errors.name}">
      <p class="error">${errors.name}</p>
    </c:if>
    <br>

    <label>Отчество:</label><br>
    <input type="text" name="patronymic"
           value="${editUser.patronymic}"><br><br>

    <label>Дата рождения:</label><br>
    <input type="date" name="birthday" required
           value="${editUser.birthday}"><br>
    <c:if test="${not empty errors.birthday}">
      <p class="error">${errors.birthday}</p>
    </c:if>
    <br>

    <label>Роль:</label><br>
    <select name="role" required>
      <option value="ADMIN" ${editUser.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
      <option value="USER" ${editUser.role == 'USER' ? 'selected' : ''}>USER</option>
    </select><br>
    <c:if test="${not empty errors.role}">
      <p class="error">${errors.role}</p>
    </c:if>
    <br>

    <button type="submit">Сохранить</button>
    <a href="${pageContext.request.contextPath}/users.jhtml">Отмена</a>
  </form>

</t:myhtml>