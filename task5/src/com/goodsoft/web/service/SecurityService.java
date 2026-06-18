package com.goodsoft.web.service;

import com.goodsoft.web.model.User;

public class SecurityService {

    private final UserService userService = new UserService();

    public UserService getUserService() {
        return userService;
    }

    public boolean login(User user) {
        return userService.login(user.getLogin(), user.getPassword());
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        return userService.changePassword(user.getLogin(), oldPassword, newPassword);
    }
}