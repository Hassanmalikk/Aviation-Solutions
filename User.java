package com.example.aviation_solutions;

public class User {
    private String username;
    private String email;
    private String password;
    private String admin;   // This can be null or "admin"
    private String phonenumber;
    private boolean selected;  // To keep track if the user is selected for notifications

    // Default constructor (required for Firebase)
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    // Constructor with parameters
    public User(String name, String email) {
        this.username = name;
        this.email = email;

        this.selected = false;  // By default, users are not selected
    }
    public User(String name, String email, String password, String admin, String phoneNumber) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.admin = admin;  // Can be null or "admin"
        this.phonenumber= phoneNumber;
        this.selected = false;  // By default, users are not selected
    }

    // Getters and setters

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
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

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phonenumber = phoneNumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
