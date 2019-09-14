package com.example.firebase_calendar.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String email;
    private String id;

    public User() {}

    public User(String email, String id) {
        this.email = email;
        this.id = id;
    }
}
