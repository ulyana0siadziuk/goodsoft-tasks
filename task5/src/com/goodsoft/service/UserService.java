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

    public long countAdmins() {
        return userDao.countAdmins();
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
}