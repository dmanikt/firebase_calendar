package com.example.firebase_calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.github.naz013.awcalendar.AwesomeCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import hirondelle.date4j.DateTime;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
//    MaterialCalendarView materialCalendarView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    AwesomeCalendarView awesomeCalendarView;
    String[] timeArray = {"12:00am", "", "", "", "1:00am", "", "", "", "2:00am", "", "", "", "3:00am", "", "", "",
                        "4:00am", "", "", "", "5:00am", "", "", "", "6:00am", "", "", "", "7:00am", "", "", "",
                        "8:00am", "", "", "", "9:00am", "", "", "", "10:00am", "", "", "", "11:00am", "", "", "",
                        "12:00pm", "", "", "", "1:00pm", "", "", "", "2:00pm", "", "", "", "3:00pm", "", "", "",
                        "4:00pm", "", "", "", "5:00pm", "", "", "", "6:00pm", "", "", "", "7:00pm", "", "", "",
                        "8:00pm", "", "", "", "9:00pm", "", "", "", "10:00pm", "", "", "", "11:00pm", "", "", "", "12:00am"};
    String[] eventArray = {"Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",
                            "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",
                            "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",
                            "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",
                            "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",
                            "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",
                            "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",
                            "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event", "Event",};
    ListView listView;

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

        if(item.getItemId() == R.id.action_add_task){
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Compare with a friend's schedule")
                    .setMessage("Enter a friend's email")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(taskEditText.getText());
                            Log.v(TAG, "Do database stuff");
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awesomeCalendarView = (AwesomeCalendarView) findViewById(R.id.calendar_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CalendarAdapter calendarAdapter = new CalendarAdapter(this, timeArray, eventArray);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(calendarAdapter);

        awesomeCalendarView.setOnDateClickListener(new AwesomeCalendarView.OnDateClickListener() {
            @Override
            public void onDateClicked(DateTime dateTime) {
                Log.d(TAG, "onDateClicked: " + dateTime);
            }
        });
        awesomeCalendarView.setOnCurrentMonthListener(new AwesomeCalendarView.OnCurrentMonthListener() {
            @Override
            public void onMonthSelected(int year, int month) {
                Log.d(TAG, "onMonthSelected: " + year + "-" + month);
            }
        });
        awesomeCalendarView.setOnDateLongClickListener(new AwesomeCalendarView.OnDateLongClickListener() {
            @Override
            public void onDateLongClicked(DateTime dateTime) {
                Log.d(TAG, "onDateLongClicked: " + dateTime);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(this);



//        WeekView mWeekView;
//        WeekView.EventClickListener mEventClickListener;
//        MonthLoader.MonthChangeListener mMonthChangeListener;
//        WeekView.EventLongPressListener mEventLongPressListener;
//        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendar_view);
//
//        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                Log.v(TAG, "Clicked");
//            }
//        });
// Get a reference for the week view in the layout.
//        mWeekView = (WeekView) findViewById(R.id.weekView);
//
//// Set an action when any event is clicked.
//        mWeekView.setOnEventClickListener(mEventClickListener);
//
//
//// The week view has infinite scrolling horizontally. We have to provide the events of a
//// month every time the month changes on the week view.
//        mWeekView.setMonthChangeListener(mMonthChangeListener);
//
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_calendar){
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_todo){
            Intent intent = new Intent(MainActivity.this, TodoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
