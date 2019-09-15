package com.example.firebase_calendar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] timeArray;
    private final String[] eventArray;

    public TodoAdapter(Activity context, String[] timeArray, String[] eventArray) {
        super(context, R.layout.item_todo, eventArray);
        this.context = context;
        this.timeArray = timeArray;
        this.eventArray = eventArray;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_todo, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView timeField = (TextView) rowView.findViewById(R.id.task_time);
        TextView eventField = (TextView) rowView.findViewById(R.id.task_title);

        //this code sets the values of the objects to values from the arrays
        timeField.setText(timeArray[position]);
        eventField.setText(eventArray[position]);

        return rowView;

    };
}
