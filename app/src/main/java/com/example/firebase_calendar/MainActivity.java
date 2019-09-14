package com.example.firebase_calendar;

import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        WeekView mWeekView;
//        WeekView.EventClickListener mEventClickListener;
//        MonthLoader.MonthChangeListener mMonthChangeListener;
//        WeekView.EventLongPressListener mEventLongPressListener;
//        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendar_view);
//
//        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//
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
}
