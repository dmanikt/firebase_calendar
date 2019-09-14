package com.example.firebase_calendar.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day {
    public ArrayList<Event> events;
    private DatabaseReference mDatabase;
    private String user;

    public Day() {
        events = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void add(Event e) {
        Event temp;
        for (int i = 0; i < events.size(); i++) {
            if (e.getStartTime() < events.get(i).getStartTime()) {
                temp = e;
                e = events.get(i);
                events.set(i, temp);
            }
        }
        events.add(e);
        Map<String, String> eventMap;
        for (int i = 0; i < events.size(); i++) {
            eventMap = new HashMap<String, String>();
            eventMap.put("title", events.get(i).getTitle());
            eventMap.put("duration", String.valueOf(events.get(i).getDuration()));
            eventMap.put("time", String.valueOf(events.get(i).getDuration()));
            mDatabase.child("users").child(user).child("events").push().setValue(eventMap);
        }
    }

    public ArrayList<Event> gaps() {
        ArrayList<Event> g = new ArrayList<>();
        for (int i = 0; i < events.size()-1; i++) {
            g.add(new Event("gap",
                    events.get(i+1).getStartTime()-events.get(i).getStartTime(),
                    events.get(i).getStartTime()));
        }
        return g;
    }
}
