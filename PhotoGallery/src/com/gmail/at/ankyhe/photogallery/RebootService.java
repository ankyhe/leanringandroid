package com.gmail.at.ankyhe.photogallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import com.gmail.at.ankyhe.photogallery.model.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-7
 * Time: AM11:43
 * To change this template use File | Settings | File Templates.
 */
public class RebootService extends BroadcastReceiver {

    private static final String LOG_TAG = RebootService.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, String.format("Intent's action is %@", intent.getAction()));

        boolean isOn = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constants.PREF_ALARM_ISON, false);
        if (isOn) {
            PollService.setServiceAlarm(context, isOn);
        }


    }


}
