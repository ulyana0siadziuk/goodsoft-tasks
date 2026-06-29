package com.goodsoft.dao;

import com.goodsoft.dao.mapper.UserMapper;
import com.goodsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMyBatisDao implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userMapper.findByLogin(login);
    }

    @Override
    public void save(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new RuntimeException("У пользователя должна быть хотя бы одна роль");
        }

        userMapper.insertUser(user);
        for (String roleName : user.getRoles()) {
            userMapper.insertUserRole(user.getId(), roleName);
        }
    }

    @Override
    public void update(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new RuntimeException("У пользователя должна быть хотя бы одна роль");
        }

        User existing = userMapper.findByLogin(user.getLogin());
        if (existing == null) {
            throw new RuntimeException("Пользователь не найден: " + user.getLogin());
        }
        user.setId(existing.getId());

        userMapper.updateUser(user);
        userMapper.deleteUserRolesByLogin(user.getLogin());
        for (String roleName : user.getRoles()) {
            userMapper.insertUserRole(user.getId(), roleName);
        }
    }

    @Override
    public void delete(String login) {
        userMapper.deleteUserRolesByLogin(login);
        userMapper.deleteUser(login);
    }

    @Override
    public boolean exists(String login) {
        return userMapper.exists(login);
    }

    @Override
    public long countAdmins() {
        return userMapper.countAdmins();
    }

    @Override
    public List<String> findAllRoles() {
        return userMapper.findAllRoles();
    }

    @Override
    public void updatePassword(String login, String newPassword) {
        int rows = userMapper.updatePassword(login, newPassword);
        if (rows == 0) {
            throw new RuntimeException("Пользователь не найден: " + login);
        }
    }
}
