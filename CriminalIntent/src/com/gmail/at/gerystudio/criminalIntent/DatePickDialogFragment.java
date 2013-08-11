package com.gmail.at.gerystudio.criminalIntent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import com.gmail.at.gerystudio.criminalIntent.model.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: PM1:59
 * To change this template use File | Settings | File Templates.
 */
public class DatePickDialogFragment extends DialogFragment {

    private Date date;

    public static DatePickDialogFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARAM_DATE, date);
        DatePickDialogFragment fragment = new DatePickDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialogfragment_datapick, null);
        DatePicker dp = (DatePicker)v.findViewById(R.id.data_picker);
        date = (Date) getArguments().getSerializable(Constants.PARAM_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);


                date = new GregorianCalendar(year, month, day, hour, minute, second).getTime();

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(Constants.PARAM_DATE, date);
            }
        });



        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.datapick_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getTargetFragment() == null) {
                            return;
                        }
                        Intent intent = new Intent();
                        intent.putExtra(Constants.PARAM_DATE, date);
                        getTargetFragment().onActivityResult(Constants.REQUESTCODE_DATE, Activity.RESULT_OK, intent);

                    }
                })
                .create();
    }
}
