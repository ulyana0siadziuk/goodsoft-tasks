package com.goodsoft.service;

import com.goodsoft.dao.UserDao;
import com.goodsoft.dao.UserInMemoryDao;
import com.goodsoft.model.Role;
import com.goodsoft.model.User;

import java.util.List;

public class UserService {

    private static UserService instance;
    private final UserDao userDao;

    private UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(UserInMemoryDao.getInstance());
        }
        return instance;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public void add(User user) {
        userDao.save(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String login) {
        userDao.delete(login);
    }

    public boolean exists(String login) {
        return userDao.exists(login);
    }

    public boolean login(String login, String password) {
        User user = userDao.findByLogin(login);
        return user != null && user.getPassword().equals(password);
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        userDao.update(user);
        return true;
    }

    public String validateDelete(String login, String currentLogin) {
        if (login == null || login.isBlank()) {
            return "Логин не указан";
        }

        User user = userDao.findByLogin(login);
        if (user == null) {
            return "Пользователь не найден";
        }

        if (login.equals(currentLogin)) {
            return "Нельзя удалить самого себя";
        }

        if (user.getRole() == Role.ADMIN && userDao.countAdmins() <= 1) {
            return "Нельзя удалить последнего администратора";
        }

        return null;
    }
}