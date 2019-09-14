package com.example.firebase_calendar.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Task {
    private String taskName;
    private String duration;

    public Task(){}

    public Task(String taskName, String duration) {
        this.taskName = taskName;
        this.duration = duration;
    }

}
