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

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}