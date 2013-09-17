package com.gmail.at.gerystudio.runnertracker.controller;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.gmail.at.gerystudio.runnertracker.model.Constants;
import com.gmail.at.gerystudio.runnertracker.model.Run;
import com.gmail.at.gerystudio.runnertracker.model.RunDatabaseHelper;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-14
 * Time: PM6:27
 * To change this template use File | Settings | File Templates.
 */
public class RunnerManager {

    /* Singleton */
    private static RunnerManager instance = null;

    private static final String PREFS_FILE = "runs";
    private static final String PREF_CURRENT_RUN_ID = "RunManager.currentRunId";

    private RunnerManager(Context aContext) {
        context = aContext;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mHelper = new RunDatabaseHelper(context);
        mPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mCurrentRunId = mPrefs.getLong(PREF_CURRENT_RUN_ID, -1);
    }

    public static RunnerManager getInstance(Context context) {
        if (instance == null) {
            instance = new RunnerManager(context);
        }
        return instance;
    }
    /* END Singleton */

    private Context context;
    private LocationManager locationManager;
    private RunDatabaseHelper mHelper;
    private SharedPreferences mPrefs;
    private long mCurrentRunId;

    public PendingIntent getPendingIntent(boolean shouldCreate) {
        Intent intent = new Intent(Constants.LOCATION_ACTION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(context, Constants.LOCATION_REQUEST_CODE, intent, flags);
    }

    public Run startNewRun() {
        // Insert a run into the db
        Run run = insertRun();
        // Start tracking the run
        startTrackingRun(run);
        return run;
    }

    public void startTrackingRun(Run run) {
        // Keep the ID
        mCurrentRunId = run.getId();
        // Store it in shared preferences
        mPrefs.edit().putLong(PREF_CURRENT_RUN_ID, mCurrentRunId).commit();
        // Start location updates
        startLocationUpdates();
    }

    public void stopRun() {
        stopLocationUpdates();
        mCurrentRunId = -1;
        mPrefs.edit().remove(PREF_CURRENT_RUN_ID).commit();
    }

    private Run insertRun() {
        Run run = new Run();
        run.setId(mHelper.insertRun(run));
        return run;
    }

    public void insertLocation(Location loc) {
        if (mCurrentRunId != -1) {
            mHelper.insertLocation(mCurrentRunId, loc);
        } else {
            Log.e(Constants.LOG_TAG, "Location received with no tracking run; ignoring.");
        }
    }

    public RunDatabaseHelper.RunCursor queryRuns() {
        return mHelper.queryRuns();
    }


    public void startLocationUpdates() {
        String provider = LocationManager.GPS_PROVIDER;
        PendingIntent pi = getPendingIntent(true);
        locationManager.requestLocationUpdates(provider, 0, 0, pi);
    }

    public void stopLocationUpdates() {
        PendingIntent pi = getPendingIntent(false);
        if (pi != null && locationManager != null) {
            locationManager.removeUpdates(pi);
            pi.cancel();
        }
    }

    public boolean isTrackingRun() {
        return getPendingIntent(false) != null;
    }

}
