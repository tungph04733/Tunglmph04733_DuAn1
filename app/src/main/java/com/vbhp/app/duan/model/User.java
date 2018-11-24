package com.vbhp.app.duan.model;

public class User {
    private  String ConformPassword;
    private  String Password;
    private  String Email;

    public User() {
    }

    public User(String conformPassword, String password, String email) {
        ConformPassword = conformPassword;
        Password = password;
        Email = email;
    }

    public String getConformPassword() {
        return ConformPassword;
    }

    public void setConformPassword(String conformPassword) {
        ConformPassword = conformPassword;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
