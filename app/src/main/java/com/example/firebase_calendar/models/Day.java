package com.example.firebase_calendar.models;

import java.util.ArrayList;

public class Day {
    public ArrayList<Event> events;

    public Day() {
        events = new ArrayList<>();
    }

    public void add(Event e) {
        Event temp;
        for (int i = 0; i < events.size(); i++) {
            if (e.startTime < events.get(i).startTime) {
                temp = e;
                e = events.get(i);
                events.set(i, temp);
            }
        }
        events.add(e);
    }

    public ArrayList<Event> gaps() {
        ArrayList<Event> g = new ArrayList<>();
        for (int i = 0; i < events.size()-1; i++) {
            g.add(new Event("gap",
                    events.get(i+1).startTime-events.get(i).startTime,
                    events.get(i).startTime));
        }
        return g;
    }
}
