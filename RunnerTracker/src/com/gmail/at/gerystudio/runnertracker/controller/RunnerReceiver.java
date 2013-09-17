package com.gmail.at.gerystudio.runnertracker.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-14
 * Time: PM6:55
 * To change this template use File | Settings | File Templates.
 */
public class RunnerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Location loc = (Location)intent
                .getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
        if (loc != null) {
            onLocationReceived(context, loc);
            return;
        }
        // If you get here, something else has happened
        if (intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {
            boolean enabled = intent
                    .getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
            onProviderEnabledChanged(enabled);
        }
    }

    protected void onLocationReceived(Context context, Location location)
    {
        // Need implemente in subclass
    }

    protected void onProviderEnabledChanged(boolean enabled)
    {
        // Need implement in subclass
    }
}
