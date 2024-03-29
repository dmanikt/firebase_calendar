package com.example.firebase_calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.firebase_calendar.db.TaskContract;
import com.example.firebase_calendar.db.TaskDbHelper;
import com.example.firebase_calendar.models.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TodoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "TodoActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter mAdapter;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private ArrayList<String> timeArray = new ArrayList<>();
    private ArrayList<String> eventArray = new ArrayList<>();

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeArray.add("30");
        timeArray.add("10");
        eventArray.add("Sleep");
        eventArray.add("Code");

        setContentView(R.layout.todo_list);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

//        mHelper = new TaskDbHelper(this);
        TodoAdapter todoAdapter = new TodoAdapter(this, R.id.list_todo, timeArray, eventArray);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mTaskListView.setAdapter(todoAdapter);
//        updateUI();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.action_add_task: {
                openDialog();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void deleteTask(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        eventArray.remove(taskTextView);
        TextView timeTextView = (TextView) parent.findViewById(R.id.task_time);
        timeArray.remove(timeTextView);
//        String task = String.valueOf(taskTextView.getText());
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.delete(TaskContract.TaskEntry.TABLE, TaskContract.TaskEntry.COL_TASK_TITLE + " =?", new String[]{task});
////        db.close();
        updateUI();
    }
    private void updateUI() {
        final ArrayList<String> taskList = new ArrayList<>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                taskList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Task task = postSnapshot.getValue(Task.class);
                    taskList.add(task.getTask());

                    // here you can access to name property like task.name

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
//        updateUI();
    }
//    private void updateUI() {
//        ArrayList<String> taskList = new ArrayList<>();
//        SQLiteDatabase db = mHelper.getReadableDatabase();
//        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
//                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
//                null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
//            taskList.add(cursor.getString(idx));
//        }
//
//        if (mAdapter == null) {
//            mAdapter = new ArrayAdapter<>(this,
//                    R.layout.item_todo,
//                    R.id.task_title,
//                    taskList);
//            mTaskListView.setAdapter(mAdapter);
//        } else {
//            mAdapter.clear();
//            mAdapter.addAll(taskList);
//            mAdapter.notifyDataSetChanged();
//        }
//
//        cursor.close();
//        db.close();
//    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_calendar){
            Intent intent = new Intent(TodoActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_todo){
            Intent intent = new Intent(TodoActivity.this, TodoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // events = [ [start1, duration1], [start2, duration2], ... ]
    public int[][] suggestMeetingTimes(int[][] events){
        ArrayList<int[]> intervals = new ArrayList<>();
        int intervalStart = -1;
        for(int time = 0; time <= 2400; time += 15) {
            // check if everybody is free
            boolean allFree = true;
            for(int[] busyPeriod : events) {
                if(busyPeriod[0] < time && (busyPeriod[0] + busyPeriod[1]) > time) {
                    allFree = false;
                    break;
                }
            }

            if(allFree && intervalStart < 0) {
                intervalStart = time;
            } else if(!allFree && intervalStart >= 0) {
                intervals.add(new int[] { intervalStart, time - 15 });
                intervalStart = -1;
            }
        }

        return (int[][]) intervals.toArray();
    }

    public int[][] getFreeTime(int[][] events){
        ArrayList<int[]> intervals = new ArrayList<>();
        int intervalStart = -1;
        for(int time = 0; time <= 2400; time += 15) {
            // check if everybody is free
            boolean free = true;
            for(int[] busyPeriod : events) {
                if(busyPeriod[0] < time && (busyPeriod[0] + busyPeriod[1]) > time) {
                    free = false;
                    break;
                }
            }

            if(free && intervalStart < 0) {
                intervalStart = time;
            } else if(!free && intervalStart >= 0) {
                intervals.add(new int[] { intervalStart, time - 15 });
                intervalStart = -1;
            }
        }

        return (int[][]) intervals.toArray();
    }
}
