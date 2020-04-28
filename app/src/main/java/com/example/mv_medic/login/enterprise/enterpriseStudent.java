package com.example.mv_medic.login.enterprise;


public class enterpriseStudent {
    String name;
    String id;
    String email;
    String add;
    String phno;

    public enterpriseStudent(String name1, String phone, String s, String name, String id) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.add = add;
        this.phno = phno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }
}
