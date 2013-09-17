package com.gmail.at.gerystudio.runnertracker.controller;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import com.gmail.at.gerystudio.runnertracker.model.Constants;

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

    private RunnerManager(Context aContext) {
        context = aContext;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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

    public PendingIntent getPendingIntent(boolean shouldCreate) {
        Intent intent = new Intent(Constants.LOCATION_ACTION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(context, Constants.LOCATION_REQUEST_CODE, intent, flags);
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
