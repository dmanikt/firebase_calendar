package com.example.firebase_calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFriendActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button mButton;
    private EditText mText;
    private String user;

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
                if (view == mButton) addFriend(mText.getText().toString());
            }
        });
    }

    public void addFriend(String name) {
        mDatabase.child("users").child(user).child("friends").push().setValue(name);
        //mDatabase.child("users").orderByChild("id").equalTo(name);
    }
}