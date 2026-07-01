package com.goodsoft.service;

import com.goodsoft.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;

@Service
public class ValidationService {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    public void validateBusinessRules(User user, boolean isEdit, BindingResult bindingResult) {
        if (!isEdit && isBlank(user.getPassword())) {
            rejectValue(bindingResult, "password", "validation.password.required");
        }

        if (!isEdit && !isBlank(user.getLogin()) && userService.exists(user.getLogin())) {
            rejectValue(bindingResult, "login", "error.login.exists");
        }

        if (user.getBirthday() != null && user.getAge() != null) {
            int calculatedAge = Period.between(user.getBirthday(), LocalDate.now()).getYears();
            if (!user.getAge().equals(calculatedAge)) {
                rejectValue(bindingResult, "age", "error.age.mismatch");
            }
        }

        List<String> roles = user.getRoles();
        if (roles != null && roles.contains("ADMIN") && roles.contains("USER")) {
            rejectValue(bindingResult, "roles", "error.roles.admin.user");
        }
    }

    public void validateDelete(String login, String currentLogin, BindingResult bindingResult) {
        User user = userService.findByLogin(login);
        if (user == null) {
            reject(bindingResult, "error.delete.notFound");
            return;
        }

        if (login.equals(currentLogin)) {
            reject(bindingResult, "error.delete.self");
            return;
        }

        if (user.isAdmin() && userService.countAdmins() <= 1) {
            reject(bindingResult, "error.delete.lastAdmin");
        }
    }

    public void validateUpdate(User user, String oldLogin, BindingResult bindingResult) {
        if (oldLogin == null || oldLogin.isBlank()) {
            return;
        }

        User existing = userService.findByLogin(oldLogin);
        if (existing == null) {
            rejectValue(bindingResult, "roles", "error.user.notFound");
            return;
        }

        if (existing.isAdmin()
                && !user.isAdmin()
                && userService.countAdmins() <= 1) {
            rejectValue(bindingResult, "roles", "error.roles.lastAdmin");
        }
    }

    private void rejectValue(BindingResult bindingResult, String field, String code) {
        bindingResult.rejectValue(field, code, msg(code));
    }

    private void reject(BindingResult bindingResult, String code) {
        bindingResult.reject(code, msg(code));
    }

    private String msg(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}