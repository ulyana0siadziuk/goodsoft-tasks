package com.goodsoft.service;

import com.goodsoft.model.User;

public class SecurityService {

    private static SecurityService instance;
    private final UserService userService;

    private SecurityService(UserService userService) {
        this.userService = userService;
    }

    public static SecurityService getInstance() {
        if (instance == null) {
            instance = new SecurityService(UserService.getInstance());
        }
        return instance;
    }

    public boolean login(String login, String password) {
        return userService.login(login, password);
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        return userService.changePassword(user, oldPassword, newPassword);
    }
}