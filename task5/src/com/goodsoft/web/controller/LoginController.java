package com.goodsoft.web.controller;

import com.goodsoft.model.User;
import com.goodsoft.service.SecurityService;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.goodsoft.web.form.LoginForm;
import com.goodsoft.web.form.ChangePasswordForm;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @ModelAttribute("loginForm") LoginForm loginForm,
            HttpSession session,
            Model model) {

        User user = securityService.login(loginForm.getLogin(), loginForm.getPassword());

        if (user != null) {
            session.setAttribute(CommonConstant.USER_KEY, user);
            return "redirect:/welcome";
        }

        model.addAttribute("errorMessage", "Неверный логин или пароль");
        return "login";
    }

    @GetMapping("/loginedit")
    public String showChangePasswordPage(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "loginedit";
    }

    @PostMapping("/loginedit")
    public String changePassword(
            @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute(CommonConstant.USER_KEY);

        if (securityService.changePassword(
                user,
                changePasswordForm.getOldPassword(),
                changePasswordForm.getNewPassword())) {

            session.setAttribute(CommonConstant.USER_KEY, user);
            return "redirect:/welcome";
        }

        model.addAttribute("errorMessage", "Неверный старый пароль");
        return "loginedit";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}