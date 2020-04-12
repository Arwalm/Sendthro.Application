package com.example.sendthro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("Schedule Event");
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }
}
