package com.example.firebase_calendar.models;

public class Event {
    private String title;
    private int duration;
    private int startTime;

    public Event(String title, String duration) {
        this.title = title;
        duration = duration.trim();
        if (duration.contains("min") || duration.contains("minutes")) {
            this.duration = Integer.valueOf(duration.split(" ")[0]);
        } else if (duration.contains("hour") || duration.contains("hr")) {
            this.duration = 60*Integer.valueOf(duration.split(" ")[0]);
        } else {
            try {
                this.duration = Integer.valueOf(duration);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public Event(String title, String duration, String startTime) {
        this(title, duration);
        startTime = startTime.trim();
        try {
            String t[] = startTime.split(":");
            this.startTime = Integer.valueOf(t[0])*60 + Integer.valueOf(t[1]);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void setStartTime(String time) {
        try {
            String t[] = time.split(":");
            this.startTime = Integer.valueOf(t[0])*60 + Integer.valueOf(t[1]);
        } catch (Exception e) {
            System.err.println(e);
        }
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
