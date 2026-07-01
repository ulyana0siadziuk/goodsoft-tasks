package com.goodsoft.web.controller;

import com.goodsoft.model.User;
import com.goodsoft.service.UserService;
import com.goodsoft.service.ValidationService;
import com.goodsoft.web.form.DeleteUserForm;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationService validationService;

    @InitBinder("editUser")
    public void initEditUserBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("deleteUserForm", new DeleteUserForm());
        return "users";
    }

    @PostMapping("/users")
    public String deleteUser(
            @Valid @ModelAttribute("deleteUserForm") DeleteUserForm deleteUserForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "users";
        }

        String login = deleteUserForm.getLogin();

        User current = (User) session.getAttribute(CommonConstant.USER_KEY);
        String currentLogin = current != null ? current.getLogin() : null;

        validationService.validateDelete(login, currentLogin, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "users";
        }

        userService.delete(login);
        return "redirect:/users";
    }

    @GetMapping("/useredit")
    public String showUserForm(
            @RequestParam(required = false) String login,
            Model model) {

        if (login != null && !login.isBlank()) {
            User user = userService.findByLogin(login);
            user.setOldLogin(login);
            model.addAttribute("editUser", user);
            model.addAttribute("editMode", true);
        } else {
            model.addAttribute("editUser", new User());
            model.addAttribute("editMode", false);
        }

        model.addAttribute("allRoles", userService.findAllRoles());
        return "useredit";
    }

    @PostMapping("/useredit")
    public String saveUser(
            @Valid @ModelAttribute("editUser") User user,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        String oldLogin = user.getOldLogin();
        boolean isEdit = oldLogin != null && !oldLogin.isBlank();

        validationService.validateBusinessRules(user, isEdit, bindingResult);

        if (isEdit) {
            validationService.validateUpdate(user, oldLogin, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", isEdit);
            model.addAttribute("allRoles", userService.findAllRoles());
            return "useredit";
        }

        if (!isEdit) {
            userService.add(user);
        } else {
            User existing = userService.findByLogin(oldLogin);
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(existing.getPassword());
            }
            userService.update(user);

            User current = (User) session.getAttribute(CommonConstant.USER_KEY);
            if (current != null && current.getLogin().equals(oldLogin)) {
                session.setAttribute(CommonConstant.USER_KEY, user);
            }
        }

        return "redirect:/users";
    }
}