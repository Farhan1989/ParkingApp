package com.example.asadmalik.parkinglifeapp;

public class Fb {

    String id;
    String Name;
    String Email;
    String Feedback;

    public Fb(){

    }

    public Fb(String id, String name, String email, String feedback) {
        this.id = id;
        Name = name;
        Email = email;
        Feedback = feedback;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getFeedback() {
        return Feedback;
    }
}
