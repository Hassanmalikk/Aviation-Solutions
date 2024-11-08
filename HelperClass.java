package com.example.aviationsolutions;

public class HelperClass {
    String name,email,username,password,cpassword;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public HelperClass(String name, String email, String username, String password, String cpassword) {
        this.name=name;
        this.email=email;
        this.username=username;
        this.password=password;
        this.cpassword = cpassword;
    }


    //default constructor
    public HelperClass() {
    }
}
