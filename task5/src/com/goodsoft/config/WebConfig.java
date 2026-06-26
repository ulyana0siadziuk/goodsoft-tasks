package com.goodsoft.config;

import com.goodsoft.web.servlet.WebDispatcherServlet;
import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class WebConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatDocumentRootCustomizer() {
        return factory -> {
            File docBase = new File("web");
            if (!docBase.isDirectory()) {
                docBase = new File(System.getProperty("user.dir"), "web");
            }
            factory.setDocumentRoot(docBase);
        };
    }

    @Bean
    public ServletRegistrationBean<DefaultServlet> defaultServletRegistration() {
        ServletRegistrationBean<DefaultServlet> registration =
                new ServletRegistrationBean<>(new DefaultServlet(), "/");
        registration.setName("default");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public WebDispatcherServlet webDispatcherServlet() {
        return new WebDispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean<WebDispatcherServlet> dispatcherServletRegistration(
            WebDispatcherServlet webDispatcherServlet) {
        ServletRegistrationBean<WebDispatcherServlet> registration =
                new ServletRegistrationBean<>(webDispatcherServlet, "*.jhtml");
        registration.setLoadOnStartup(2);
        return registration;
    }
}