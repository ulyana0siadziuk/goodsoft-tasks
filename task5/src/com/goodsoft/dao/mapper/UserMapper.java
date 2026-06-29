package com.goodsoft.dao.mapper;

import com.goodsoft.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll();

    User findByLogin(@Param("login") String login);

    int insertUser(User user);

    int updateUser(User user);

    int deleteUserRolesByLogin(@Param("login") String login);

    int deleteUser(@Param("login") String login);

    int insertUserRole(@Param("userId") int userId, @Param("roleName") String roleName);

    boolean exists(@Param("login") String login);

    long countAdmins();

    List<String> findAllRoles();

    int updatePassword(@Param("login") String login, @Param("newPassword") String newPassword);
}
