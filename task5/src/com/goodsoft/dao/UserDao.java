package com.goodsoft.dao;

import com.goodsoft.model.User;
import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findByLogin(String login);
    void save(User user);
    void update(User user);
    void delete(String login);
    boolean exists(String login);
    long countAdmins();
}