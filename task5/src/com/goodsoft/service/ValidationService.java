package com.goodsoft.service;

import com.goodsoft.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidationService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");

    public Map<String, String> validateUser(User user, boolean isEdit, UserService userService) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(user.getLogin())) {
            errors.put("login", "Логин обязателен");
        } else if (!isEdit && userService.exists(user.getLogin())) {
            errors.put("login", "Пользователь с таким логином уже существует");
        }

        if (isBlank(user.getPassword())) {
            errors.put("password", "Пароль обязателен");
        }

        if (isBlank(user.getEmail())) {
            errors.put("email", "Email обязателен");
        } else if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            errors.put("email", "Некорректный формат email");
        }

        if (isBlank(user.getSurname())) {
            errors.put("surname", "Фамилия обязательна");
        }

        if (isBlank(user.getName())) {
            errors.put("name", "Имя обязательно");
        }

        if (user.getBirthday() == null) {
            errors.put("birthday", "Дата рождения обязательна");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            errors.put("birthday", "Дата рождения не может быть в будущем");
        }

        if (user.getRole() == null) {
            errors.put("role", "Роль обязательна");
        }

        return errors;
    }

    public LocalDate parseBirthday(String birthdayStr, Map<String, String> errors) {
        if (isBlank(birthdayStr)) {
            errors.put("birthday", "Дата рождения обязательна");
            return null;
        }
        try {
            return LocalDate.parse(birthdayStr);
        } catch (DateTimeParseException e) {
            errors.put("birthday", "Некорректный формат даты");
            return null;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}