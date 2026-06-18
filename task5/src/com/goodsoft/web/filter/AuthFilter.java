package com.goodsoft.web.filter;

import com.goodsoft.web.model.Role;
import com.goodsoft.web.model.User;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String servletPath = req.getServletPath();
        if (servletPath == null || servletPath.isEmpty()) {
            servletPath = CommonConstant.INDEX_PAGE;
        }

        HttpSession session = req.getSession(false);
        User userInfo = null;
        if (session != null) {
            userInfo = (User) session.getAttribute(CommonConstant.USER_INFO_KEY);
        }

        if (CommonConstant.LOGIN_JHTML.equals(servletPath) && userInfo != null) {
            resp.sendRedirect(req.getContextPath() + CommonConstant.WELCOME_PAGE + ".jhtml");
            return;
        }

        if (isPublicPath(servletPath)) {
            chain.doFilter(request, response);
            return;
        }

        if (userInfo != null) {
            if (isAdminOnlyPath(servletPath) && userInfo.getRole() != Role.ADMIN) {
                resp.sendRedirect(req.getContextPath() + CommonConstant.WELCOME_PAGE + ".jhtml");
                return;
            }
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + CommonConstant.LOGIN_JHTML);
        }
    }

    private boolean isPublicPath(String servletPath) {
        return CommonConstant.LOGIN_JHTML.equals(servletPath)
                || CommonConstant.INDEX_PAGE.equals(servletPath)
                || CommonConstant.INDEX_JSP.equals(servletPath)
                || servletPath.startsWith("/css/");
    }

    private boolean isAdminOnlyPath(String servletPath) {
        return ("/" + CommonConstant.USERS_PAGE + ".jhtml").equals(servletPath)
                || servletPath.startsWith("/" + CommonConstant.USEREDIT_PAGE + ".jhtml");
    }
}