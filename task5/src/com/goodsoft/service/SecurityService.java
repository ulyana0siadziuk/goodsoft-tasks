package com.goodsoft.service;

import com.goodsoft.model.User;

public class SecurityService {

    private static SecurityService instance;
    private final UserService userService;

    private SecurityService(UserService userService) {
        this.userService = userService;
    }

    static synchronized SecurityService getInstance(UserService userService) {
        if (instance == null) {
            instance = new SecurityService(userService);
        }
        return instance;
    }

    public User login(String login, String password) {
        return userService.login(login, password);
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        return userService.changePassword(user, oldPassword, newPassword);
    }
}