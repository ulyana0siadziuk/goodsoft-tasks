package com.goodsoft.web.controller;

import com.goodsoft.model.User;
import com.goodsoft.security.SecurityUtils;
import com.goodsoft.service.SecurityService;
import com.goodsoft.web.form.ChangePasswordForm;
import com.goodsoft.web.form.LoginForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/loginedit")
    public String showChangePasswordPage(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "loginedit";
    }

    @PostMapping("/loginedit")
    public String changePassword(
            @Valid @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "loginedit";
        }

        User user = SecurityUtils.getCurrentUser();

        if (securityService.changePassword(
                user,
                changePasswordForm.getOldPassword(),
                changePasswordForm.getNewPassword())) {
            return "redirect:/welcome";
        }

        bindingResult.reject("error.password.invalid", msg("error.password.invalid"));
        return "loginedit";
    }

    private String msg(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }
}