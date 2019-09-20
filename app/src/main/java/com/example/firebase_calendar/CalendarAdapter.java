package com.example.firebase_calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;

public class CalendarAdapter extends ArrayAdapter {
    private final Activity context;
    private final String[] timeArray;
    private final String[] eventArray;
    private final String[] eventArray2;

    public CalendarAdapter(Activity context, String[] timeArray, String[] eventArray, String[] eventArray2) {
        super(context, R.layout.calendar_item, eventArray);
        this.context = context;
        this.timeArray = timeArray;
        this.eventArray = eventArray;
        this.eventArray2 = eventArray2;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.calendar_item, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView timeField = (TextView) rowView.findViewById(R.id.time);
        TextView eventField = (TextView) rowView.findViewById(R.id.event_info);
        TextView eventField2 = (TextView) rowView.findViewById(R.id.event_info2);

        //this code sets the values of the objects to values from the arrays
        timeField.setText(timeArray[position]);
        eventField.setText(eventArray[position]);
        eventField2.setText(eventArray2[position]);

        if (eventArray[position].equals("Event") && !eventArray2[position].equals("Event")) {
            rowView.setBackgroundColor(Color.LTGRAY);
        }

        return rowView;

    };
}
