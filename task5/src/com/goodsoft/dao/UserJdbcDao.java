package com.goodsoft.dao;

import com.goodsoft.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserJdbcDao implements UserDao {

    private static final String SELECT_USERS_WITH_ROLES = """
            SELECT u.login, u.password, u.name, u.birth_date, u.age, u.salary, r.name AS role_name
            FROM users u
            LEFT JOIN user_roles ur ON u.id = ur.user_id
            LEFT JOIN roles r ON ur.role_id = r.id
            """;

    @Override
    public List<User> findAll() {
        String sql = SELECT_USERS_WITH_ROLES + " ORDER BY u.login, r.name";

        Map<String, User> usersByLogin = new LinkedHashMap<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                addRowToMap(usersByLogin, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить список пользователей: " + e.getMessage(), e);
        }

        return new ArrayList<>(usersByLogin.values());
    }

    @Override
    public User findByLogin(String login) {
        String sql = SELECT_USERS_WITH_ROLES + " WHERE u.login = ? ORDER BY r.name";

        Map<String, User> usersByLogin = new LinkedHashMap<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    addRowToMap(usersByLogin, rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти пользователя '" + login + "': " + e.getMessage(), e);
        }

        return usersByLogin.get(login);
    }

    @Override
    public void save(User user) {
        String insertUserSql = """
                INSERT INTO users (login, password, name, birth_date, age, salary)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);

            try {
                int userId;

                try (PreparedStatement ps = conn.prepareStatement(
                        insertUserSql, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, user.getLogin());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, user.getName());
                    setBirthDate(ps, 4, user.getBirthday());
                    setInteger(ps, 5, user.getAge());
                    setInteger(ps, 6, user.getSalary());
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("Не удалось получить id нового пользователя");
                        }
                        userId = keys.getInt(1);
                    }
                }

                insertUserRoles(conn, userId, user.getRoles());

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить пользователя '" + user.getLogin() + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) {
        String updateUserSql = """
                UPDATE users
                SET password = ?, name = ?, birth_date = ?, age = ?, salary = ?
                WHERE login = ?
                """;

        String deleteRolesSql = """
                DELETE FROM user_roles
                WHERE user_id = (SELECT id FROM users WHERE login = ?)
                """;

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement ps = conn.prepareStatement(updateUserSql)) {
                    ps.setString(1, user.getPassword());
                    ps.setString(2, user.getName());
                    setBirthDate(ps, 3, user.getBirthday());
                    setInteger(ps, 4, user.getAge());
                    setInteger(ps, 5, user.getSalary());
                    ps.setString(6, user.getLogin());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteRolesSql)) {
                    ps.setString(1, user.getLogin());
                    ps.executeUpdate();
                }

                int userId = findUserId(conn, user.getLogin());
                insertUserRoles(conn, userId, user.getRoles());

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить пользователя '" + user.getLogin() + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void updatePassword(String login, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE login = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, login);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Пользователь не найден: " + login);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить пароль пользователя '" + login + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String login) {
        String deleteRolesSql = """
                DELETE FROM user_roles
                WHERE user_id = (SELECT id FROM users WHERE login = ?)
                """;

        String deleteUserSql = """
                DELETE FROM users
                WHERE login = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement ps = conn.prepareStatement(deleteRolesSql)) {
                    ps.setString(1, login);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteUserSql)) {
                    ps.setString(1, login);
                    ps.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить пользователя '" + login + "': " + e.getMessage(), e);
        }
    }

    @Override
    public boolean exists(String login) {
        String sql = "SELECT 1 FROM users WHERE login = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось проверить существование пользователя '" + login + "': " + e.getMessage(), e);
        }
    }

    @Override
    public long countAdmins() {
        String sql = """
                SELECT COUNT(DISTINCT u.id)
                FROM users u
                JOIN user_roles ur ON u.id = ur.user_id
                JOIN roles r ON ur.role_id = r.id
                WHERE r.name = 'ADMIN'
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подсчитать администраторов: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> findAllRoles() {
        String sql = "SELECT name FROM roles ORDER BY name";
        List<String> roles = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roles.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить список ролей: " + e.getMessage(), e);
        }

        return roles;
    }

    private void addRowToMap(Map<String, User> usersByLogin, ResultSet rs) throws SQLException {
        String login = rs.getString("login");
        User user = usersByLogin.get(login);

        if (user == null) {
            user = mapUserFields(rs);
            usersByLogin.put(login, user);
        }

        String roleName = rs.getString("role_name");
        if (roleName != null && !user.getRoles().contains(roleName)) {
            user.getRoles().add(roleName);
        }
    }

    private User mapUserFields(ResultSet rs) throws SQLException {
        User user = new User();
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));

        Date birthDate = rs.getDate("birth_date");
        if (birthDate != null) {
            user.setBirthday(birthDate.toLocalDate());
        }

        int age = rs.getInt("age");
        if (!rs.wasNull()) {
            user.setAge(age);
        }

        int salary = rs.getInt("salary");
        if (!rs.wasNull()) {
            user.setSalary(salary);
        }

        return user;
    }

    private void insertUserRoles(Connection conn, int userId, List<String> roles) throws SQLException {
        if (roles == null || roles.isEmpty()) {
            throw new SQLException("У пользователя должна быть хотя бы одна роль");
        }

        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String roleName : roles) {
                int roleId = findRoleId(conn, roleName);
                ps.setInt(1, userId);
                ps.setInt(2, roleId);
                ps.executeUpdate();
            }
        }
    }

    private int findUserId(Connection conn, String login) throws SQLException {
        String sql = "SELECT id FROM users WHERE login = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        throw new SQLException("Пользователь не найден: " + login);
    }

    private int findRoleId(Connection conn, String roleName) throws SQLException {
        String sql = "SELECT id FROM roles WHERE name = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roleName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        throw new SQLException("Роль не найдена в БД: " + roleName);
    }

    private void setBirthDate(PreparedStatement ps, int index, LocalDate birthday)
            throws SQLException {
        if (birthday == null) {
            ps.setDate(index, null);
        } else {
            ps.setDate(index, Date.valueOf(birthday));
        }
    }

    private void setInteger(PreparedStatement ps, int index, Integer value)
            throws SQLException {
        if (value == null) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, value);
        }
    }
}