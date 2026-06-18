package com.goodsoft.web.servlet;

import com.goodsoft.web.model.Role;
import com.goodsoft.web.model.User;
import com.goodsoft.web.service.SecurityService;
import com.goodsoft.web.service.ValidationService;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebDispatcherServlet extends HttpServlet {

    private final SecurityService securityService = new SecurityService();
    private final ValidationService validationService = new ValidationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String servletPath = req.getServletPath();
        String pageName = servletPath.substring(1, servletPath.length() - ".jhtml".length());

        if (CommonConstant.LOGIN_PAGE.equals(pageName)) {
            handleLogin(req, resp);
            return;
        }
        if (CommonConstant.LOGOUT_PAGE.equals(pageName)) {
            handleLogout(req, resp);
            return;
        }
        if (CommonConstant.LOGINEDIT_PAGE.equals(pageName)) {
            handleLoginEdit(req, resp);
            return;
        }
        if (CommonConstant.USERS_PAGE.equals(pageName)) {
            handleUsers(req, resp);
            return;
        }
        if (CommonConstant.USEREDIT_PAGE.equals(pageName)) {
            handleUserEdit(req, resp);
            return;
        }

        req.getRequestDispatcher(CommonConstant.JSP_PATH + pageName + ".jsp")
                .forward(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter(CommonConstant.ACTION_PARAM);

        if (CommonConstant.LOGIN_ACTION_PERFORM.equals(action)) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            if (securityService.getUserService().login(login, password)) {
                User user = securityService.getUserService().findByLogin(login);
                req.getSession().setAttribute(CommonConstant.USER_INFO_KEY, user);
                resp.sendRedirect(req.getContextPath() + CommonConstant.WELCOME_PAGE + ".jhtml");
            } else {
                req.setAttribute("errorMessage", "Неверный логин или пароль");
                req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGIN_PAGE + ".jsp")
                        .forward(req, resp);
            }
        } else {
            req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGIN_PAGE + ".jsp")
                    .forward(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + CommonConstant.LOGIN_JHTML);
    }

    private void handleLoginEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userInfo = (User) session.getAttribute(CommonConstant.USER_INFO_KEY);
        String action = req.getParameter(CommonConstant.ACTION_PARAM);

        if (CommonConstant.CHANGE_PASSWORD_ACTION.equals(action)) {
            String oldPassword = req.getParameter("oldPassword");
            String newPassword = req.getParameter("newPassword");

            if (securityService.changePassword(userInfo, oldPassword, newPassword)) {
                session.setAttribute(CommonConstant.USER_INFO_KEY, userInfo);
                resp.sendRedirect(req.getContextPath() + CommonConstant.WELCOME_PAGE + ".jhtml");
            } else {
                req.setAttribute("errorMessage", "Неверный старый пароль");
                req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGINEDIT_PAGE + ".jsp")
                        .forward(req, resp);
            }
        } else {
            req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGINEDIT_PAGE + ".jsp")
                    .forward(req, resp);
        }
    }

    private void handleUsers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter(CommonConstant.ACTION_PARAM);

        if (CommonConstant.DELETE_USER_ACTION.equals(action)) {
            String login = req.getParameter("login");
            securityService.getUserService().delete(login);
            resp.sendRedirect(req.getContextPath() + "/" + CommonConstant.USERS_PAGE + ".jhtml");
            return;
        }

        req.setAttribute("users", securityService.getUserService().findAll());
        req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.USERS_PAGE + ".jsp")
                .forward(req, resp);
    }

    private void handleUserEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter(CommonConstant.ACTION_PARAM);
        var userService = securityService.getUserService();

        if (CommonConstant.SAVE_USER_ACTION.equals(action)) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String surname = req.getParameter("surname");
            String name = req.getParameter("name");
            String patronymic = req.getParameter("patronymic");
            String birthdayStr = req.getParameter("birthday");
            String roleStr = req.getParameter("role");
            String oldLogin = req.getParameter("oldLogin");

            boolean isEdit = oldLogin != null && !oldLogin.isBlank();

            Map<String, String> errors = new HashMap<>();

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setBirthday(validationService.parseBirthday(birthdayStr, errors));

            Role role = null;
            if (roleStr != null && !roleStr.isBlank()) {
                try {
                    role = Role.valueOf(roleStr);
                } catch (IllegalArgumentException e) {
                    errors.put("role", "Некорректная роль");
                }
            }
            user.setRole(role);

            errors.putAll(validationService.validateUser(user, isEdit, userService));

            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                req.setAttribute("editUser", user);
                req.setAttribute("editMode", isEdit);
                req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.USEREDIT_PAGE + ".jsp")
                        .forward(req, resp);
                return;
            }

            if (!isEdit) {
                userService.add(user);
            } else {
                if (!oldLogin.equals(login)) {
                    userService.delete(oldLogin);
                }
                userService.update(user);

                HttpSession session = req.getSession(false);
                if (session != null) {
                    User current = (User) session.getAttribute(CommonConstant.USER_INFO_KEY);
                    if (current != null && current.getLogin().equals(oldLogin)) {
                        session.setAttribute(CommonConstant.USER_INFO_KEY, user);
                    }
                }
            }

            resp.sendRedirect(req.getContextPath() + "/" + CommonConstant.USERS_PAGE + ".jhtml");
            return;
        }

        String login = req.getParameter("login");
        if (login != null && !login.isBlank()) {
            req.setAttribute("editUser", userService.findByLogin(login));
            req.setAttribute("editMode", true);
        } else {
            req.setAttribute("editMode", false);
        }

        req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.USEREDIT_PAGE + ".jsp")
                .forward(req, resp);
    }
}