package com.goodsoft.web.form;

import jakarta.validation.constraints.NotBlank;

public class LoginForm {

    @NotBlank(message = "{validation.login.required}")
    private String login;

    @NotBlank(message = "{validation.password.required}")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}