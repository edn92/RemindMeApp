package com.example.remindme;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Edward on 2/03/2015.
 * Generates a notification. Vibrates for half a second, and gives a blinking LED light
 */
public class AlarmReceiver extends BroadcastReceiver {
    private int notificationID;
    DbAdapter dbAdapter;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationID = intent.getExtras().getInt("ID");

        long[] pattern = {400, 500};

        Notification notification = new Notification.Builder(context)
                .setContentTitle(intent.getExtras().getString("title"))
                .setContentText(intent.getExtras().getString("description"))
                .setSmallIcon(R.drawable.ic_launcher)
                .setVibrate(pattern)
                .setLights(255, 500, 1000)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID, notification);

        dbAdapter = new DbAdapter(context);
        dbAdapter.deleteAlarm(dbAdapter.getAlarm(intent.getExtras().getInt("ID")));
        //delete set_alarm from set_alarm table
    }
}
