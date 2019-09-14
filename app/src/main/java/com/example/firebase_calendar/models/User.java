package com.example.firebase_calendar.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class User {
    public String email;
    public String id;
    public ArrayList<String> friends;
    public User() {}

    public User(String id, String email) {
        this.email = email;
        this.id = id;
        this.friends = new ArrayList<>();
    }

    public void addFriend(String id) {
        friends.add(id);
    }
}
