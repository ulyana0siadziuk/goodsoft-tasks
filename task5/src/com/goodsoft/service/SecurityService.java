package com.goodsoft.service;

import com.goodsoft.dao.UserDao;
import com.goodsoft.model.User;

public class SecurityService {

    private final UserDao userDao;
    private final UserService userService;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
        this.userService = new UserService(userDao);
    }

    public UserService getUserService() {
        return userService;
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
}