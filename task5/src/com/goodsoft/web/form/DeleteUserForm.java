package com.goodsoft.web.form;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserForm {

    @NotBlank(message = "{validation.delete.login.required}")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}