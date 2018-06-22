package com.github.gridswipe_launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {

    // Used to start the launcher after the device is booted
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
