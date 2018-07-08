package com.example.asadmalik.parkinglifeapp;

public class User {

    String userId;
    String userName;
    String userArea;

    public User(){

    }

    public User(String userId, String userName, String userArea) {
        this.userId = userId;
        this.userName = userName;
        this.userArea = userArea;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserArea() {
        return userArea;
    }
}
