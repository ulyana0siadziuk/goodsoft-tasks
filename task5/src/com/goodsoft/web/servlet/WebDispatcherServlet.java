package com.goodsoft.web.servlet;

import com.goodsoft.model.Role;
import com.goodsoft.model.User;
import com.goodsoft.service.SecurityService;
import com.goodsoft.service.ValidationService;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.goodsoft.dao.UserDao;
import com.goodsoft.dao.UserInMemoryDao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebDispatcherServlet extends HttpServlet {

    private final SecurityService securityService = new SecurityService(new UserInMemoryDao());
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
        String pagePath = servletPath.substring(0, servletPath.length() - ".jhtml".length());

        if (CommonConstant.LOGIN_PAGE.equals(pagePath)) {
            handleLogin(req, resp);
            return;
        }
        if (CommonConstant.LOGOUT_PAGE.equals(pagePath)) {
            handleLogout(req, resp);
            return;
        }
        if (CommonConstant.LOGINEDIT_PAGE.equals(pagePath)) {
            handleLoginEdit(req, resp);
            return;
        }
        if (CommonConstant.USERS_PAGE.equals(pagePath)) {
            handleUsers(req, resp);
            return;
        }
        if (CommonConstant.USEREDIT_PAGE.equals(pagePath)) {
            handleUserEdit(req, resp);
            return;
        }

        req.getRequestDispatcher(CommonConstant.JSP_PATH + pagePath.substring(1) + ".jsp")
                .forward(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter(CommonConstant.ACTION_PARAM);

        if (CommonConstant.LOGIN_ACTION_PERFORM.equals(action)) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            if (securityService.login(login, password)) {
                User user = securityService.getUserService().findByLogin(login);
                req.getSession().setAttribute(CommonConstant.USER_KEY, user);
                resp.sendRedirect(req.getContextPath() + CommonConstant.WELCOME_PAGE + ".jhtml");
            } else {
                req.setAttribute("errorMessage", "Неверный логин или пароль");
                req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGIN_PAGE.substring(1) + ".jsp")
                        .forward(req, resp);
            }
        } else {
            req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGIN_PAGE.substring(1) + ".jsp")
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
        User user = (User) session.getAttribute(CommonConstant.USER_KEY);
        String action = req.getParameter(CommonConstant.ACTION_PARAM);

        if (CommonConstant.CHANGE_PASSWORD_ACTION.equals(action)) {
            String oldPassword = req.getParameter("oldPassword");
            String newPassword = req.getParameter("newPassword");

            if (securityService.changePassword(user, oldPassword, newPassword)) {
                session.setAttribute(CommonConstant.USER_KEY, user);
                resp.sendRedirect(req.getContextPath() + CommonConstant.WELCOME_PAGE + ".jhtml");
            } else {
                req.setAttribute("errorMessage", "Неверный старый пароль");
                req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGINEDIT_PAGE.substring(1) + ".jsp")
                        .forward(req, resp);
            }
        } else {
            req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.LOGINEDIT_PAGE.substring(1) + ".jsp")
                    .forward(req, resp);
        }
    }

    private void handleUsers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter(CommonConstant.ACTION_PARAM);

        if (CommonConstant.DELETE_USER_ACTION.equals(action)) {
            String login = req.getParameter("login");
            var userService = securityService.getUserService();

            HttpSession session = req.getSession(false);
            User current = session != null ? (User) session.getAttribute(CommonConstant.USER_KEY) : null;
            String currentLogin = current != null ? current.getLogin() : null;

            String error = userService.validateDelete(login, currentLogin);
            if (error == null) {
                userService.delete(login);
                resp.sendRedirect(req.getContextPath() + CommonConstant.USERS_PAGE + ".jhtml");
            } else {
                req.setAttribute("errorMessage", error);
                req.setAttribute("users", userService.findAll());
                req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.USERS_PAGE.substring(1) + ".jsp")
                        .forward(req, resp);
            }
            return;
        }

        req.setAttribute("users", securityService.getUserService().findAll());
        req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.USERS_PAGE.substring(1) + ".jsp")
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
                req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.USEREDIT_PAGE.substring(1) + ".jsp")
                        .forward(req, resp);
                return;
            }

            if (!isEdit) {
                userService.add(user);
            } else {
                userService.update(user);

                HttpSession session = req.getSession(false);
                if (session != null) {
                    User current = (User) session.getAttribute(CommonConstant.USER_KEY);
                    if (current != null && current.getLogin().equals(oldLogin)) {
                        session.setAttribute(CommonConstant.USER_KEY, user);
                    }
                }
            }

            resp.sendRedirect(req.getContextPath() + CommonConstant.USERS_PAGE + ".jhtml");
            return;
        }

        String login = req.getParameter("login");
        if (login != null && !login.isBlank()) {
            req.setAttribute("editUser", userService.findByLogin(login));
            req.setAttribute("editMode", true);
        } else {
            req.setAttribute("editMode", false);
        }

        req.getRequestDispatcher(CommonConstant.JSP_PATH + CommonConstant.USEREDIT_PAGE.substring(1) + ".jsp")
                .forward(req, resp);
    }
}