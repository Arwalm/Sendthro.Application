package com.example.sendthro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

class Notification_Reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent (context, SaudiEvents.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100 ,
                intent1, PendingIntent.FLAG_UPDATE_CURRENT);


        //                     Schedule The Event                          //
        Intent activity = new Intent (context, CreateSmsScheduleActivity.class);
        PendingIntent contentintent = PendingIntent.getActivity(context,
                0,activity,0);
        Intent reciver = new Intent (context, NotificationReciver.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(context,
                0,reciver, PendingIntent.FLAG_UPDATE_CURRENT);
        ////////////////////////////////////////////////////////////

        Notification note = new NotificationCompat.Builder(context)
                .setContentTitle("Saudi Annual Events")
                .setContentText("Title")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentIntent(pendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(contentintent)
                .addAction(R.drawable.ic_navtime, "Schedule this event", contentintent)
                .setOnlyAlertOnce(true)
                .setColor(Color.DKGRAY)
                .build();

        notificationManager.notify(100, note);

    }
}
