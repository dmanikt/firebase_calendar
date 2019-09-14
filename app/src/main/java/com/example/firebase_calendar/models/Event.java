package com.example.firebase_calendar.models;

public class Event {
    private String title;
    private int duration;
    private int startTime;

    public Event(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public Event(String title, int duration, int startTime) {
        this(title, duration);
        this.startTime = startTime;
    }

    public void setStartTime(int time) {
        this.startTime = time;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getStartTime() {
        return startTime;
    }
}
