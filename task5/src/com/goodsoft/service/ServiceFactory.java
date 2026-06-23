package com.goodsoft.service;

import com.goodsoft.dao.UserDao;

public final class ServiceFactory {

    private ServiceFactory() {
    }

    public static UserService createUserService(UserDao userDao) {
        return UserService.getInstance(userDao);
    }

    public static SecurityService createSecurityService(UserService userService) {
        return SecurityService.getInstance(userService);
    }
}
