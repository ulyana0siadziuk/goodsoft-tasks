package com.goodsoft.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private Integer id;

    @NotBlank(message = "{validation.login.required}")
    private String login;

    private String oldLogin;

    private String password;

    @NotBlank(message = "{user.name.required}")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "{user.birthday.required}")
    @PastOrPresent(message = "{user.birthday.future}")
    private LocalDate birthday;

    @NotNull(message = "{user.age.required}")
    @Min(value = 18, message = "{user.age.min}")
    private Integer age;

    @NotNull(message = "{user.salary.required}")
    @Min(value = 0, message = "{user.salary.min}")
    private Integer salary;

    @NotEmpty(message = "{user.roles.required}")
    private List<String> roles = new ArrayList<>();

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldLogin() {
        return oldLogin;
    }

    public void setOldLogin(String oldLogin) {
        this.oldLogin = oldLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    public boolean isAdmin() {
        return roles != null && roles.contains("ADMIN");
    }

    public String getRolesAsString() {
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return String.join(", ", roles);
    }

    public Date getBirthdayDate() {
        if (birthday == null) {
            return null;
        }
        return Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}