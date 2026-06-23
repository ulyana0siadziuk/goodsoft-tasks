package com.goodsoft.service;

import com.goodsoft.dao.DaoFactory;
import com.goodsoft.dao.UserDao;

public class ServiceFactory {

    private ServiceFactory() {
    }

    public static UserService createUserService() {
        UserDao userDao = DaoFactory.createUserDao();
        return UserService.getInstance(userDao);
    }

    public static SecurityService createSecurityService() {
        UserService userService = createUserService();
        return SecurityService.getInstance(userService);
    }
}
