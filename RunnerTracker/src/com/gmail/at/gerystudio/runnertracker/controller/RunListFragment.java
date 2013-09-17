package com.gmail.at.gerystudio.runnertracker.controller;


import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gmail.at.gerystudio.runnertracker.model.Constants;
import com.gmail.at.gerystudio.runnertracker.model.Run;
import com.gmail.at.gerystudio.runnertracker.model.RunDatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-17
 * Time: PM11:25
 * To change this template use File | Settings | File Templates.
 */
public class RunListFragment extends ListFragment {

    private RunDatabaseHelper.RunCursor mCursor;

    private TrackingLocationReceiver receiver = new TrackingLocationReceiver();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RunnerManager.getInstance(getActivity()).startNewRun();

        // Query the list of runs
        mCursor = RunnerManager.getInstance(getActivity()).queryRuns();
        // Create an adapter to point at this cursor
        RunCursorAdapter adapter = new RunCursorAdapter(getActivity(), mCursor);
        setListAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        mCursor.close();
        super.onDestroy();
    }

    private static class RunCursorAdapter extends CursorAdapter {

        private RunDatabaseHelper.RunCursor mRunCursor;

        public RunCursorAdapter(Context context, RunDatabaseHelper.RunCursor cursor) {
            super(context, cursor, 0);
            mRunCursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the run for the current row
            Run run = mRunCursor.getRun();

            // Set up the start date text view
            TextView startDateTextView = (TextView)view;
            String cellText;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cellText = df.format(run.getStartDate());
            startDateTextView.setText(cellText);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(receiver,
                new IntentFilter(Constants.LOCATION_ACTION));
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(receiver);
        super.onStop();
    }
}
