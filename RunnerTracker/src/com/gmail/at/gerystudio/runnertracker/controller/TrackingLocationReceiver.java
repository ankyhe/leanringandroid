package com.gmail.at.gerystudio.runnertracker.controller;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.gmail.at.gerystudio.runnertracker.model.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-17
 * Time: PM11:18
 * To change this template use File | Settings | File Templates.
 */
public class TrackingLocationReceiver extends RunnerReceiver {

    @Override
    protected void onLocationReceived(Context c, Location loc) {
        RunnerManager.getInstance(c).insertLocation(loc);
        Log.d(Constants.LOG_TAG, "insert location");
    }

}
