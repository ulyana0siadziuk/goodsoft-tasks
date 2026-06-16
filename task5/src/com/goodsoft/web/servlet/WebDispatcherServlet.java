package com.goodsoft.web.servlet;

import com.goodsoft.web.model.UserInfo;
import com.goodsoft.web.service.SecurityService;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class WebDispatcherServlet extends HttpServlet {

    private final SecurityService securityService = new SecurityService();

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

        req.getRequestDispatcher(CommonConstant.JSP_PATH + pageName + ".jsp")
                .forward(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter(CommonConstant.ACTION_PARAM);

        if (CommonConstant.LOGIN_ACTION_PERFORM.equals(action)) {
            UserInfo userInfo = new UserInfo(
                    req.getParameter("login"),
                    req.getParameter("password")
            );

            if (securityService.login(userInfo)) {
                req.getSession().setAttribute(CommonConstant.USER_INFO_KEY, userInfo);
                resp.sendRedirect(req.getContextPath() + CommonConstant.WELCOME_PAGE + ".jhtml");
            } else {
                req.setAttribute("errorMessage", "Error login or password");
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
        UserInfo userInfo = (UserInfo) session.getAttribute(CommonConstant.USER_INFO_KEY);
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
}
