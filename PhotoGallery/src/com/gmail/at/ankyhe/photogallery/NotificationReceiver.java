package com.gmail.at.ankyhe.photogallery;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-7
 * Time: PM1:04
 * To change this template use File | Settings | File Templates.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (getResultCode() != Activity.RESULT_OK) {
            Log.i("BroadcastReceiver", "Resultcode is not OK");
            return;
        }

        Log.i("BroadcastReceiver", "Resultcode is OK");
        int requestCode = intent.getIntExtra("REQUEST_CODE", 0);
        Notification notification = (Notification)
                intent.getParcelableExtra("NOTIFICATION");

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(requestCode, notification);

    }
}
