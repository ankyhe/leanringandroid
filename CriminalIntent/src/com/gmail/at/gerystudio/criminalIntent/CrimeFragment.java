package com.gmail.at.gerystudio.criminalIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gmail.at.gerystudio.criminalIntent.model.Constants;
import com.gmail.at.gerystudio.criminalIntent.model.Crime;
import com.gmail.at.gerystudio.criminalIntent.model.CrimeRepos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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

    public static CrimeFragment newInstance(UUID uuid) {
        CrimeFragment crimeFragment = new CrimeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARAM_UUID, uuid);
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        UUID uuid = (UUID) bundle.getSerializable(Constants.PARAM_UUID);
        Crime aCrime = CrimeRepos.getInstance(getActivity()).getCrimeByUUID(uuid);
        if (aCrime != null) {
            crime = aCrime;
        }
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
                if (crime != null) {
                    crime.setTitle(editable.toString());
                }
            }
        });

        if (crime != null) {
            titleEditText.setText(crime.getTitle());
            Button datetimeButton = (Button)view.findViewById(R.id.datetime_button);
            datetimeButton.setText(crime.getDatetimeStr());
            datetimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DatePickDialogFragment fragment;
                    fragment = DatePickDialogFragment.newInstance(new Date(crime.getDatetime()));
                    fragment.setTargetFragment(CrimeFragment.this, Constants.REQUESTCODE_DATE);
                    fragment.show(fm, "date");
                }
            });
            CheckBox solvedCheckBox = (CheckBox)view.findViewById(R.id.solved_checkbox);
            solvedCheckBox.setChecked(crime.isSolved());
            solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.d(LOG_TAG, "ENTER checkbox changes");
                    crime.setSolved(b);
                }
            });
        } else {
            Log.e(LOG_TAG, "crime is null.");
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUESTCODE_DATE) {
            if (resultCode == Activity.RESULT_OK) {
                Date date = (Date) data.getSerializableExtra(Constants.PARAM_DATE);
                crime.setDatetime(date);
                Button datetimeButton = (Button)getView().findViewById(R.id.datetime_button);
                datetimeButton.setText(crime.getDatetimeStr());
            }
        }
    }
}
