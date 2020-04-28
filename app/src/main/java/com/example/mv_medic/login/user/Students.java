package com.example.mv_medic.login.user;

public class Students {
    private String email;
    private String Adress;
    private String city;
    private String dob;
    private String phone;
    private String name;
    private String password;

    public Students(String email, String adress, String city, String dob, String phone, String name, String password) {
        this.email = email;
        this.Adress = adress;
        this.city = city;
        this.dob = dob;
        this.phone = phone;
        this.name = name;
        this.password = password;

    }

    public Students(String name, String email, String phone, String address, String city, String dob) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return this.name + "- " + email + "-" + phone + "-" + dob + "-" + city + "-" + Adress;
    }
}

