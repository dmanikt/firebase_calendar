package com.example.firebase_calendar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase_calendar.models.Day;
import com.example.firebase_calendar.models.Event;
import com.example.firebase_calendar.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewTaskActivity extends AppCompatActivity {

    private static final String TAG = "NewTaskActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTaskField;
    private EditText mDurationField;
    private EditText mStartField;
    private Button mSubmitButton;

    private Day day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mTaskField = findViewById(R.id.fieldTask);
        mDurationField = findViewById(R.id.fieldDuration);
        mStartField = findViewById(R.id.startTime);
        mSubmitButton = findViewById(R.id.addTask);

        day = new Day();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTask();
            }
        });
    }

    private void submitTask() {
        final String task = mTaskField.getText().toString();
        final String duration = mDurationField.getText().toString();
        final String start = mStartField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(task)) {
            mTaskField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(duration)) {
            mDurationField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(start)) {
            mStartField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        //setEditingEnabled(false);
        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();

        // [START single_value_read]

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        day.add(new Event(task, duration, start));

        /*mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewTaskActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewTask(userId, task, duration);
                        }

                        // Finish this Activity, back to the stream
                        //setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        //setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });*/
        // [END single_value_read]
    }

    /*private void setEditingEnabled(boolean enabled) {
        mTaskField.setEnabled(enabled);
        mDurationField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }*/

    private void writeNewTask(String userId, String task, String duration) {
        // Create new post at /user-tasks/$userid/$taskid and at
        // /tasks/$taskid simultaneously
        String key = mDatabase.child("tasks").push().getKey();

        ///
        ///
    }
    // [END write_fan_out]
}
