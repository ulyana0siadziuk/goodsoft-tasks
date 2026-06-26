package com.goodsoft.dao;

public final class DaoFactory {

    private DaoFactory() {
    }

    public static UserDao createUserDao() {
        return new UserJdbcDao();
    }
}
