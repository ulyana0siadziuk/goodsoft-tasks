package com.goodsoft.web.service;

import com.goodsoft.web.model.Role;
import com.goodsoft.web.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        initTestUsers();
    }

    private void initTestUsers() {
        User admin = new User("admin", "admin", Role.ADMIN);
        admin.setEmail("admin@gmail.com");
        admin.setSurname("Сердюк");
        admin.setName("Ульяна");
        admin.setPatronymic("Владимировна");
        admin.setBirthday(LocalDate.of(2000, 5, 20));

        User user1 = new User("user1", "user1", Role.USER);
        user1.setEmail("user1@gmail.com");
        user1.setSurname("Иванов");
        user1.setName("Иван");
        user1.setPatronymic("Иванович");
        user1.setBirthday(LocalDate.of(1995, 5, 20));

        User user2 = new User("user2", "user2", Role.USER);
        user2.setEmail("user2@gmail.com");
        user2.setSurname("Сидоров");
        user2.setName("Сидор");
        user2.setPatronymic("Сидорович");
        user2.setBirthday(LocalDate.of(1990, 2, 1));

        users.add(admin);
        users.add(user1);
        users.add(user2);
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public User findByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public boolean login(String login, String password) {
        User user = findByLogin(login);
        return user != null && user.getPassword().equals(password);
    }

    public void add(User user) {
        users.add(user);
    }

    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(user.getLogin())) {
                users.set(i, user);
                return;
            }
        }
    }

    public void delete(String login) {
        users.removeIf(user -> user.getLogin().equals(login));
    }

    public long countAdmins() {
        return users.stream().filter(user -> user.getRole() == Role.ADMIN).count();
    }

    public String validateDelete(String login, String currentLogin) {
        if (login == null || login.isBlank()) {
            return "Логин не указан";
        }
        User user = findByLogin(login);
        if (user == null) {
            return "Пользователь не найден";
        }
        if (login.equals(currentLogin)) {
            return "Нельзя удалить самого себя";
        }
        if (user.getRole() == Role.ADMIN && countAdmins() <= 1) {
            return "Нельзя удалить последнего администратора";
        }
        return null;
    }

    public boolean exists(String login) {
        return findByLogin(login) != null;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        User user = findByLogin(login);
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        return true;
    }
}