package com.goodsoft.service;

import com.goodsoft.model.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public LocalDate parseBirthday(String birthdayStr, Map<String, String> errors) {
        if (isBlank(birthdayStr)) {
            return null;
        }
        try {
            return LocalDate.parse(birthdayStr);
        } catch (DateTimeParseException e) {
            errors.put("birthday", "Некорректный формат даты");
            return null;
        }
    }

    public Integer parseInteger(String value, String fieldName, Map<String, String> errors) {
        if (isBlank(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            errors.put(fieldName, "Введите целое число");
            return null;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}