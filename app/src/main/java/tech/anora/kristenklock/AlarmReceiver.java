package tech.anora.kristenklock;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


import java.util.Calendar;
import java.util.List;

/**
 * Created by cmakohon on 4/20/17.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver implements SensorEventListener {

    private SensorManager sm;
    private Sensor accelSensor;
    private Sensor lightSensor;
    private List<Sensor> l;
    boolean noLight = true;
    boolean noMotion = true;
    Ringtone r;
    Intent _intent;

    Context _context;
    @Override
    public void onReceive(Context context, Intent intent) {

        _context = context;
        _intent = intent;

        Log.v("TAG", "Alarm has gone off!");

        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        l = sm.getSensorList(Sensor.TYPE_ALL);

        if(sm.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        if(sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(context, u);
        r.play();

        showNotifications();

    }

    public void showNotifications()
    {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(_context)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                        .setContentTitle("Kristen Klock")
                        .setContentText("Time to wake up!");

        // Gets an instance of the NotificationManager service//
        NotificationManager mNotificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

        Intent resultIntent = new Intent(_context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(_context);
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getStringType().equals(Sensor.STRING_TYPE_ACCELEROMETER)) {
            float x = (float) Math.pow(event.values[0], 2);
            float y = (float) Math.pow(event.values[1], 2);
            float z = (float) Math.pow(event.values[2], 2);
            float val = (float) Math.sqrt(x + y + z);
//            Log.v("ACCEL: ", "" + val);
            if(val > 13.0) {
                noMotion = false;
            }
        }

        if(event.sensor.getStringType().equals(Sensor.STRING_TYPE_LIGHT)) {
            float val = event.values[0];
//            Log.v("LIGHT: ", "" + val);
            if(val > 400.0) {
                noLight = false;
            }
        }

        if(!noMotion && !noLight) {
            r.stop();
            int alarmID = _intent.getExtras().getInt("alarmID");
            Log.v("TAG", "" + alarmID);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(_context, alarmID, _intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmIntent.cancel();
            MainActivity.cancelAlarm(_context, _intent, alarmID);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
