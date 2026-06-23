package com.goodsoft.dao;

public class DaoFactory {

    private DaoFactory() {
    }

    public static UserDao createUserDao() {
        return UserInMemoryDao.getInstance();
    }
}
