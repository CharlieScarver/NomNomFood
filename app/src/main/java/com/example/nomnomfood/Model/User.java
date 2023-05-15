package com.example.nomnomfood.Model;

public class User {
    // Field names have to match key names in Firebase!
    private String Name;
    private String Password;

    private String Phone;

    public User() {
    }

    public User(String name, String password) {
        this.Name = name;
        this.Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
