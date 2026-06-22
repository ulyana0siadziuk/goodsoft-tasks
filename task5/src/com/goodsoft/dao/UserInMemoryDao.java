package com.goodsoft.dao;

import com.goodsoft.model.Role;
import com.goodsoft.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserInMemoryDao implements UserDao {

    private static UserInMemoryDao instance;
    private final List<User> users = new ArrayList<>();

    private UserInMemoryDao() {
        initTestUsers();
    }

    public static synchronized  UserInMemoryDao getInstance() {
        if (instance == null) {
            instance = new UserInMemoryDao();
        }
        return instance;
    }

    private void initTestUsers() {
        User admin = new User("admin", "admin", Role.ADMIN);
        admin.setEmail("admin@gmail.com");
        admin.setSurname("Сердюк");
        admin.setName("Ульяна");
        admin.setPatronymic("Владимировна");
        admin.setBirthday(LocalDate.of(2000, 5, 20));

        User user1 = new User("user1", "user1", Role.USER);
        user1.setEmail("user1@gmail.com");
        user1.setSurname("Иванов");
        user1.setName("Иван");
        user1.setPatronymic("Иванович");
        user1.setBirthday(LocalDate.of(1995, 5, 20));

        User user2 = new User("user2", "user2", Role.USER);
        user2.setEmail("user2@gmail.com");
        user2.setSurname("Сидоров");
        user2.setName("Сидор");
        user2.setPatronymic("Сидорович");
        user2.setBirthday(LocalDate.of(1990, 2, 1));

        users.add(admin);
        users.add(user1);
        users.add(user2);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User findByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(user.getLogin())) {
                users.set(i, user);
                return;
            }
        }
    }

    @Override
    public void delete(String login) {
        users.removeIf(user -> user.getLogin().equals(login));
    }

    @Override
    public boolean exists(String login) {
        return findByLogin(login) != null;
    }

    @Override
    public long countAdmins() {
        return users.stream()
                .filter(user -> user.getRole() == Role.ADMIN)
                .count();
    }
}