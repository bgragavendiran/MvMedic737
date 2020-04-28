package com.example.mv_medic.login.user;


public class User {
    private String phone;
    private String password;

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public User() {

    }

    public String getEmail() {
        return phone;
    }

    public void setEmail(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
