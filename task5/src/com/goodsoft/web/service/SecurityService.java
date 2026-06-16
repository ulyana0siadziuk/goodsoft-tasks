package com.goodsoft.web.service;

import com.goodsoft.web.model.UserInfo;

public class SecurityService {

    private String storedPassword = "admin";

    public boolean login(UserInfo userInfo) {
        return "admin".equals(userInfo.getLogin())
                && storedPassword.equals(userInfo.getPassword());
    }

    public boolean changePassword(UserInfo userInfo, String oldPassword, String newPassword) {
        if (!"admin".equals(userInfo.getLogin())) {
            return false;
        }
        if (!storedPassword.equals(oldPassword)) {
            return false;
        }
        storedPassword = newPassword;
        userInfo.setPassword(newPassword);
        return true;
    }
}
