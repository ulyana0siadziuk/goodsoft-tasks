package com.goodsoft.service;

import com.goodsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ValidationService {

    @Autowired
    private UserService userService;

    public void validateBusinessRules(User user, boolean isEdit, BindingResult bindingResult) {
        if (!isEdit && isBlank(user.getPassword())) {
            bindingResult.rejectValue("password", "", "Пароль обязателен");
        }

        if (!isEdit && !isBlank(user.getLogin()) && userService.exists(user.getLogin())) {
            bindingResult.rejectValue("login", "", "Пользователь с таким логином уже существует");
        }

        if (user.getBirthday() != null && user.getAge() != null) {
            int calculatedAge = Period.between(user.getBirthday(), LocalDate.now()).getYears();
            if (!user.getAge().equals(calculatedAge)) {
                bindingResult.rejectValue("age", "", "Возраст не соответствует дате рождения");
            }
        }

        List<String> roles = user.getRoles();
        if (roles != null && roles.contains("ADMIN") && roles.contains("USER")) {
            bindingResult.rejectValue("roles", "", "Роли USER и ADMIN нельзя выбирать одновременно");
        }
    }

    public void validateDelete(String login, String currentLogin, BindingResult bindingResult) {
        User user = userService.findByLogin(login);
        if (user == null) {
            bindingResult.reject("error.delete", "Пользователь не найден");
            return;
        }

        if (login.equals(currentLogin)) {
            bindingResult.reject("error.delete", "Нельзя удалить самого себя");
            return;
        }

        if (user.isAdmin() && userService.countAdmins() <= 1) {
            bindingResult.reject("error.delete", "Нельзя удалить последнего администратора");
        }
    }

    public void validateUpdate(User user, String oldLogin, BindingResult bindingResult) {
        if (oldLogin == null || oldLogin.isBlank()) {
            return;
        }

        User existing = userService.findByLogin(oldLogin);
        if (existing == null) {
            bindingResult.rejectValue("roles", "", "Пользователь не найден");
            return;
        }

        if (existing.isAdmin()
                && !user.isAdmin()
                && userService.countAdmins() <= 1) {
            bindingResult.rejectValue("roles", "",
                    "Нельзя снять роль администратора у последнего администратора");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}