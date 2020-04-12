package com.example.sendthro;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;

    Cursor cursor;


    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Uri uri = intent.getData();

            //Display a notification to view the task details
            Intent action = new Intent(this, AddReminderActivity.class);
            action.setData(uri);
            PendingIntent operation = TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(action)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            //Grab the task description
            if (uri != null) {
                cursor = getContentResolver().query(uri, null, null, null, null);
            }

        String description = "";

        try {
                if (cursor != null && cursor.moveToFirst()) {
                    description = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            ///////////

        Intent activity = new Intent (this, CreateSmsScheduleActivity.class);
        PendingIntent contentintent = PendingIntent.getActivity(this,
                0,activity,0);
        Intent reciver = new Intent (this, NotificationReciver.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0,reciver, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification note = new NotificationCompat.Builder(this)
                    .setContentIntent(contentintent)
                    .setContentTitle(getString(R.string.reminder_title))
                    .setContentText(description)
                    .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setAutoCancel(true)
                    .addAction(R.mipmap.ic_launcher, "Schedule this event", actionIntent)
                    .setOnlyAlertOnce(true)
                    .setColor(Color.DKGRAY)
                    .build();
//                    .setContentIntent(operation)

            manager.notify(NOTIFICATION_ID, note);


    }
}