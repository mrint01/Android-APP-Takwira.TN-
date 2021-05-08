package com.example.takwiratn.models;

public class User {
    public String fullname,email,username,password,phone,type;

    public User(){

    }

    public User(String fullname, String email, String username, String password, String phone,String type) {
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.type = type;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
