package com.example.firebase_calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase_calendar.models.Event;
import com.example.firebase_calendar.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button mButton;
    private EditText mText;
    private String user;
    private String friend;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mText = findViewById(R.id.friend);
        mButton = findViewById(R.id.add_friend);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friend = mText.getText().toString();
                if (view == mButton) addFriend(friend);
                display();
            }
        });
    }

    public void display() {
        //final ArrayList<String> task = new ArrayList<>();
        mDatabase.child("users").orderByChild("id").equalTo(friend).addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                Event event = dataSnapshot.getChildren().iterator().next().child("events").getChildren().iterator().next().getValue(Event.class);
                System.out.println(dataSnapshot);
                System.out.println(user);
                toast(event);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void toast(Event event) {
        Toast.makeText(this, event.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public void addFriend(String name) {
        mDatabase.child("users").child(user).child("friends").push().setValue(name);
        //mDatabase.child("users").orderByChild("id").equalTo(name);
    }
}