package com.goodsoft.service;

import com.goodsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UserService userService;

    public User login(String login, String password) {
        return userService.login(login, password);
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        return userService.changePassword(user, oldPassword, newPassword);
    }
}