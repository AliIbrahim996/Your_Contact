package com.example.your_contact.Model;

public class User {
    private String id;
    private String email;
    private String user_name;

    public User(String id, String email, String user_name) {
        this.id = id;
        this.email = email;
        this.user_name = user_name;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
