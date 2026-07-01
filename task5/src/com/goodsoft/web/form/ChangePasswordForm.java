package com.goodsoft.web.form;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordForm {

    @NotBlank(message = "{validation.oldPassword.required}")
    private String oldPassword;

    @NotBlank(message = "{validation.newPassword.required}")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}