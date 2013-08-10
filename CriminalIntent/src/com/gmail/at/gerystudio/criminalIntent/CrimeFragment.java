package com.gmail.at.gerystudio.criminalIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.gmail.at.gerystudio.criminalIntent.model.Crime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-10
 * Time: PM8:28
 * To change this template use File | Settings | File Templates.
 */
public class CrimeFragment extends Fragment {
    private Crime crime;

    private static final String LOG_TAG = CrimeFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        EditText titleEditText = (EditText)view.findViewById(R.id.crime_title);
        titleEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                crime.setTitle(editable.toString());
            }
        });

        Button datetimeButton = (Button)view.findViewById(R.id.datetime_button);
        Date date = new Date(crime.getDatetime());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        datetimeButton.setText(df.format(date));

        CheckBox solvedCheckBox = (CheckBox)view.findViewById(R.id.solved_checkbox);
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(LOG_TAG, "ENTER checkbox changes");
                crime.setSolved(b);
            }
        });

        return view;
    }
}
