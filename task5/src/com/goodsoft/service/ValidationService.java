package com.goodsoft.service;

import com.goodsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValidationService {

    @Autowired
    private UserService userService;

    public Map<String, String> validateUser(User user, boolean isEdit) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(user.getLogin())) {
            errors.put("login", "Логин обязателен");
        } else if (!isEdit && userService.exists(user.getLogin())) {
            errors.put("login", "Пользователь с таким логином уже существует");
        }

        if (isBlank(user.getPassword())) {
            errors.put("password", "Пароль обязателен");
        }

        if (isBlank(user.getName())) {
            errors.put("name", "Имя обязательно");
        }

        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            errors.put("birthday", "Дата рождения не может быть в будущем");
        }

        if (user.getAge() != null && user.getAge() < 0) {
            errors.put("age", "Возраст не может быть отрицательным");
        } else if (user.getAge() != null && user.getAge() < 18) {
            errors.put("age", "Возраст должен быть не менее 18 лет");
        } else if (user.getBirthday() != null && user.getAge() != null) {
            int calculatedAge = Period.between(user.getBirthday(), LocalDate.now()).getYears();
            if (!user.getAge().equals(calculatedAge)) {
                errors.put("age", "Возраст не соответствует дате рождения");
            }
        }

        if (user.getSalary() != null && user.getSalary() < 0) {
            errors.put("salary", "Зарплата не может быть отрицательной");
        }

        List<String> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            errors.put("roles", "Выберите хотя бы одну роль");
        } else if (roles.contains("ADMIN") && roles.contains("USER")) {
            errors.put("roles", "Роли USER и ADMIN нельзя выбирать одновременно");
        }

        return errors;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}