package com.example.remindme;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Edward on 2/03/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationID = intent.getExtras().getInt("ID");

        Notification notification = new Notification.Builder(context)
                .setContentTitle(intent.getExtras().getString("title"))
                .setContentText(intent.getExtras().getString("description"))
                .setSmallIcon(R.drawable.ic_launcher)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID, notification);

        Log.d("Note: ", "" + notificationID);
    }
}
