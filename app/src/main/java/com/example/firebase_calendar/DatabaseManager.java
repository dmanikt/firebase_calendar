package com.example.firebase_calendar;

import android.support.annotation.NonNull;

import com.example.firebase_calendar.models.Event;
import com.example.firebase_calendar.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DatabaseManager {
    private String user;
    private DatabaseReference mDatabase;

    public DatabaseManager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public void addFriend(String friend) {
        mDatabase.child("users").child(user).child("friends").push().setValue(friend);
    }
    public void addEvent(String title, String duration, String startTime) {
        Map<String, Object> events = new HashMap<>();
        events.put("title", title);
        events.put("duration", duration);
        events.put("time", startTime);
        mDatabase.child("users").child(user).child("events").push().setValue(events);
    }
    public ArrayList<User> getFriends() {
        final ArrayList<User> u = new ArrayList<>();
        mDatabase.child("users").child(user).child("friends").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator().next().child("friends").getChildren().iterator();
                        while (i.hasNext()) {
                            u.add(i.next().getValue(User.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        return u;
    }
    public ArrayList<Event> getEvents(String name) {
        final ArrayList<Event> e = new ArrayList<>();
        mDatabase.child("users").orderByChild("id").equalTo(name).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot);
                        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator().next().child("events").getChildren().iterator();
                        while (i.hasNext()) {
                            Event elt = i.next().getValue(Event.class);
                            e.add(elt);
                            System.out.println("////" + elt.getTitle());
                            MainActivity.staticEvents.add(elt);
                        }
                        System.out.println("size /// " + e.size());
                        MainActivity.s = true;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        return e;
    }
}
