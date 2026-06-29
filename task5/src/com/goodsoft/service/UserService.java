package com.goodsoft.service;

import com.goodsoft.dao.UserDao;
import com.goodsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public List<String> findAllRoles() {
        return userDao.findAllRoles();
    }

    @Transactional
    public void add(User user) {
        userDao.save(user);
    }

    @Transactional
    public void update(User user) {
        User existing = userDao.findByLogin(user.getLogin());
        if (existing == null) {
            throw new RuntimeException("Пользователь не найден: " + user.getLogin());
        }
        user.setId(existing.getId());
        userDao.update(user);
    }

    @Transactional
    public void delete(String login) {
        userDao.delete(login);
    }

    public boolean exists(String login) {
        return userDao.exists(login);
    }

    public User login(String login, String password) {
        User user = userDao.findByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }
        if (!exists(user.getLogin())) {
            return false;
        }
        userDao.updatePassword(user.getLogin(), newPassword);
        user.setPassword(newPassword);
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

        if (user.isAdmin() && userDao.countAdmins() <= 1) {
            return "Нельзя удалить последнего администратора";
        }

        return null;
    }

    public String validateUpdate(User user, String oldLogin) {
        if (oldLogin == null || oldLogin.isBlank()) {
            return null;
        }

        User existing = userDao.findByLogin(oldLogin);
        if (existing == null) {
            return "Пользователь не найден";
        }

        if (existing.isAdmin()
                && !user.isAdmin()
                && userDao.countAdmins() <= 1) {
            return "Нельзя снять роль администратора у последнего администратора";
        }

        return null;
    }
}