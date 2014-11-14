package me.tatarka.alarmtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public static final long ALARM_DELAY = 1000 * 60 * 2; // 2 minutes
    public static final int MAX_ALARMS = 4000;
    public static final String ACTION_ALARM_FIRED = "me.tatarka.alarmtest.ACTION_ALARM_FIRED";

    private static int alarmFiredCount = 0;
    AlarmManager am;
    AlarmBomb bomb;
    TextView alarmsSet;
    TextView alarmsFired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        
        setContentView(R.layout.activity_main);

        alarmsSet = (TextView) findViewById(R.id.alarms_set);
        alarmsFired = (TextView) findViewById(R.id.alarms_fired);
        
        bomb = new AlarmBomb();
        bomb.execute();

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ACTION_ALARM_FIRED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bomb != null) {
            bomb.cancel(true);
            bomb = null;
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    private class AlarmBomb extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 1; i <= MAX_ALARMS; i++) {
                Intent intent = new Intent(MainActivity.this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + ALARM_DELAY, pendingIntent);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            alarmsSet.setText("" + values[0]);
        }
    }
    
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            alarmFiredCount += 1;
            alarmsFired.setText("" + alarmFiredCount);
        }
    };
}
