package com.goodsoft.model;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class User {
    private String login;
    private String password;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private Role role;

    public User() {
    }

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;

    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getBirthdayDate() {
        if (birthday == null) {
            return null;
        }
        return Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (surname != null) {
            sb.append(surname);
        }
        if (name != null) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(name);
        }
        if (patronymic != null) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(patronymic);
        }
        return sb.toString();
    }
}
