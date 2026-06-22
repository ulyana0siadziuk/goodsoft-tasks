package com.goodsoft.service;

import com.goodsoft.model.User;

public class SecurityService {

    private final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public boolean login(String login, String password) {
        return userService.login(login, password);
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        return userService.changePassword(user, oldPassword, newPassword);
    }
}