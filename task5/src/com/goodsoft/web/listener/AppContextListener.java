package com.goodsoft.web.listener;

import com.goodsoft.dao.DaoFactory;
import com.goodsoft.dao.UserDao;
import com.goodsoft.service.SecurityService;
import com.goodsoft.service.ServiceFactory;
import com.goodsoft.service.UserService;
import com.goodsoft.web.util.CommonConstant;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserDao userDao = DaoFactory.createUserDao();
        UserService userService = ServiceFactory.createUserService(userDao);
        SecurityService securityService = ServiceFactory.createSecurityService(userService);
        context.setAttribute(CommonConstant.USER_SERVICE_KEY, userService);
        context.setAttribute(CommonConstant.SECURITY_SERVICE_KEY, securityService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}