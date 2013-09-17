package com.gmail.at.gerystudio.runnertracker.controller;

import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.gmail.at.gerystudio.runnertracker.R;
import com.gmail.at.gerystudio.runnertracker.model.Constants;
import com.gmail.at.gerystudio.runnertracker.model.Run;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-14
 * Time: PM6:07
 * To change this template use File | Settings | File Templates.
 */
public class RunnerTrackerFragment extends Fragment {

    private TextView locationTextView;

    private TextView durationTextView;

    private TextView startTextView;

    private Location location;

    private Button startButton;

    private Button stopButton;

    private Run run;

    private RunnerReceiver receiver = new RunnerReceiver() {
        @Override
        protected void onLocationReceived(Context context, Location aLocation) {
            Log.d(Constants.LOG_TAG, "Receive");
            location =  aLocation;
            updateUI();
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled) {
            Toast.makeText(getActivity(), R.string.gps_disabled_text, 4);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_runnertracker, container, false);
        locationTextView = (TextView)v.findViewById(R.id.location_textView);
        startTextView = (TextView)v.findViewById(R.id.last_updatetime);
        durationTextView = (TextView)v.findViewById(R.id.duration_time);
        final RunnerManager rm = RunnerManager.getInstance(getActivity());
        startButton = (Button) v.findViewById(R.id.start_button);
        stopButton = (Button)v.findViewById(R.id.stop_button);
        startButton.setEnabled(!rm.isTrackingRun());
        stopButton.setEnabled(rm.isTrackingRun());
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rm.startLocationUpdates();
                run = new Run();
                updateUI();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rm.stopLocationUpdates();
                updateUI();
            }
        });

        return v;
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

    private void updateUI() {
        RunnerManager rm = RunnerManager.getInstance(getActivity());
        if (run != null) {
            startTextView.setText(run.getStartDate().toString());
        }

        int durationSeconds = 0;
        if (run != null && location != null) {
            durationSeconds = run.getDurationSeconds(location.getTime());
            String locStr = String.format("La:%.6f Lo:%.6f At:%.6f",
                    location.getLatitude(), location.getLongitude(), location.getAltitude());
            locationTextView.setText(locStr);
        }
        durationTextView.setText(Run.formatDuration(durationSeconds));
        startButton.setEnabled(!rm.isTrackingRun());
        stopButton.setEnabled(rm.isTrackingRun());
    }
}
