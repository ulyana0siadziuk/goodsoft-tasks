package com.goodsoft.web.controller;

import com.goodsoft.model.User;
import com.goodsoft.service.UserService;
import com.goodsoft.service.ValidationService;
import com.goodsoft.web.form.DeleteUserForm;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.http.HttpSession;
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

import java.util.HashMap;
import java.util.Map;

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
            @ModelAttribute("deleteUserForm") DeleteUserForm deleteUserForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        String login = deleteUserForm.getLogin();

        User current = (User) session.getAttribute(CommonConstant.USER_KEY);
        String currentLogin = current != null ? current.getLogin() : null;

        String error = userService.validateDelete(login, currentLogin);
        if (error == null) {
            userService.delete(login);
            return "redirect:/users";
        }

        bindingResult.reject("error.delete", error);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("deleteUserForm", new DeleteUserForm());
        return "users";
    }

    @GetMapping("/useredit")
    public String showUserForm(
            @RequestParam(required = false) String login,
            Model model) {

        if (login != null && !login.isBlank()) {
            model.addAttribute("editUser", userService.findByLogin(login));
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
            @ModelAttribute("editUser") User user,
            BindingResult bindingResult,
            @RequestParam(required = false) String oldLogin,
            HttpSession session,
            Model model) {

        boolean isEdit = oldLogin != null && !oldLogin.isBlank();
        Map<String, String> errors = new HashMap<>(validationService.validateUser(user, isEdit));

        if (isEdit) {
            String updateError = userService.validateUpdate(user, oldLogin);
            if (updateError != null) {
                errors.put("roles", updateError);
            }
        }

        errors.forEach((field, message) ->
                bindingResult.rejectValue(field, "", message));

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", isEdit);
            model.addAttribute("allRoles", userService.findAllRoles());
            return "useredit";
        }

        if (!isEdit) {
            userService.add(user);
        } else {
            userService.update(user);

            User current = (User) session.getAttribute(CommonConstant.USER_KEY);
            if (current != null && current.getLogin().equals(oldLogin)) {
                session.setAttribute(CommonConstant.USER_KEY, user);
            }
        }

        return "redirect:/users";
    }
}