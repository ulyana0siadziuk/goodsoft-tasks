package com.goodsoft.web.form;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserForm {

    @NotBlank(message = "Логин не указан")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}