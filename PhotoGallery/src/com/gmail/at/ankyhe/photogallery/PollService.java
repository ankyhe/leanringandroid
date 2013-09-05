package com.gmail.at.ankyhe.photogallery;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.gmail.at.ankyhe.photogallery.controller.FlickrFetcher;
import com.gmail.at.ankyhe.photogallery.model.Constants;
import com.gmail.at.ankyhe.photogallery.model.GalleryItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-5
 * Time: PM7:39
 * To change this template use File | Settings | File Templates.
 */
public class PollService extends IntentService {

    private final static String TAG = PollService.class.getName();

    private static final int POLL_INTERVAL = 1000 * 15; // 15 seconds

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Receive Intent: " + intent);
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        boolean isNetworkAvailable = cm.getBackgroundDataSetting() &&
                cm.getActiveNetworkInfo() != null;
        if (!isNetworkAvailable) {
            Log.i(TAG, "Network isn't available.");
            return;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = prefs.getString(Constants.PREF_SEARCH_QUERY, null);
        String lastResultId = prefs.getString(Constants.PREF_LAST_RESULT_ID, null);

        ArrayList<GalleryItem> items;
        if (query != null && !query.equals("")) {
            Log.d(TAG, "query is " + query);
            items = new FlickrFetcher().search(query);
        } else {
            Log.d(TAG, "get recent");
            items = new FlickrFetcher().fetchItems();
        }

        if (items.size() == 0) {
            Log.i(TAG, "items size is 0");
            return;
        }

        String resultId = items.get(0).getId();
        if (!resultId.equals(lastResultId)) {
            Log.i(TAG, "Got a new result: " + resultId);
            Resources r = getResources();
            PendingIntent pi = PendingIntent
                    .getActivity(this, 0, new Intent(this, PhotoGalleryActivity.class), 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(r.getString(R.string.new_pictures_title))
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle(r.getString(R.string.new_pictures_title))
                    .setContentText(r.getString(R.string.new_pictures_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, notification);
        } else {
            Log.i(TAG, "Got an old result: " + resultId);
        }
        prefs.edit()
                .putString(Constants.PREF_LAST_RESULT_ID, resultId)
                .commit();


    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setRepeating(AlarmManager.RTC,
                    System.currentTimeMillis(), POLL_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }
}
