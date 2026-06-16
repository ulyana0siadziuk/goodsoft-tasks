package com.goodsoft.web.filter;

import com.goodsoft.web.model.UserInfo;
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

        if (CommonConstant.LOGIN_JHTML.equals(servletPath)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        UserInfo userInfo = null;

        if (session != null) {
            userInfo = (UserInfo) session.getAttribute(CommonConstant.USER_INFO_KEY);
        }

        if (userInfo != null) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + CommonConstant.LOGIN_JHTML);
        }
    }
}
