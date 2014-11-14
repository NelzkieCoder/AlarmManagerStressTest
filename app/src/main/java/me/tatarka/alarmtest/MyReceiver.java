package me.tatarka.alarmtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LocalBroadcastManager lm = LocalBroadcastManager.getInstance(context);
        lm.sendBroadcast(new Intent(MainActivity.ACTION_ALARM_FIRED));
    }
}
