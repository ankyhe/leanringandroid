package com.gmail.at.gerystudio.criminalIntent.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.gmail.at.gerystudio.criminalIntent.R;
import com.gmail.at.gerystudio.criminalIntent.model.Crime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: AM12:45
 * To change this template use File | Settings | File Templates.
 */
public class CrimeArrayAdapter extends ArrayAdapter<Crime> {

    private Activity activity;

    public CrimeArrayAdapter(List<Crime> crimes, Activity aActivity) {
        super(aActivity, 0, crimes);
        activity = aActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(R.layout.list_item_crime, null);
        }

        // Configure the view for this Crime
        Crime crime = getItem(position);

        TextView titleTextView =
                (TextView)convertView.findViewById(R.id.list_item_crime_title);
        titleTextView.setText(crime.getTitle());
        TextView dateTextView =
                (TextView)convertView.findViewById(R.id.list_item_crime_datetime);
        dateTextView.setText(crime.getDatetimeStr());
        CheckBox solvedCheckBox =
                (CheckBox)convertView.findViewById(R.id.list_item_crime_solved_checkbox);
        solvedCheckBox.setChecked(crime.isSolved());

        return convertView;
    }

}
