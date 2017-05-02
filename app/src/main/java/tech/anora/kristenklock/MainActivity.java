package tech.anora.kristenklock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TimePicker time;
    private static AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;
    private int alarmID = 7; //start at a non-zero number
    private static double light_threshold;
    private CalibrationDialog calibrate;
    private static List<SensorAlarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarms = new ArrayList<SensorAlarm>();
        light_threshold = 200.0;

        context = this.getApplicationContext();
        alarmMgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        calibrate = new CalibrationDialog();
        calibrate.show(getSupportFragmentManager(), "calibrate");

        time = (TimePicker) findViewById(R.id.timePicker);
    }

    public void setAlarm(View v) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmID", alarmID);
        alarmIntent = PendingIntent.getBroadcast(context, alarmID, intent, 0);

        int hour = time.getHour();
        int minute = time.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        //if the alarm is set for a time that has already passed in the
        //current day, set it for the next day
        if(System.currentTimeMillis() >= calendar.getTimeInMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        SensorAlarm alarm = new SensorAlarm(calendar, 20.0, alarmID, context, intent);
        alarms.add(alarm);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        showSetToast(hour,minute);

        alarmID++;

        Log.v("TAG", "Hour: " + hour + ", Minute: " + minute);
        Log.v("TAG", "Alarm " + alarm.get_alarmID() + " has been created at " + calendar.getTimeInMillis());

    }

    protected void showSetToast(int hour, int minute)
    {
        //Convert time to 12 hour time for toast
        int timeHour;
        String timeMin = Integer.toString(minute);
        String amOrPm;
        if (hour > 12)
        {
            timeHour = hour % 12;
            amOrPm = "PM";
        }
        else
        {
            timeHour = hour;
            amOrPm = "AM";
        }

        if(minute < 10)
            timeMin = "0" + timeMin;

        CharSequence text = "Alarm set for " + timeHour + ":" + timeMin + " " + amOrPm;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 285);
        toast.show();
    }

    public void launchAlarmsList(View view)
    {
        Intent myIntent = new Intent(this, TimeList.class);
        this.startActivity(myIntent);
    }

    public void showCalibrationDialog(View v) {
        calibrate.show(getSupportFragmentManager(), "calibrate");
    }

    public static void setLightThreshhold(double val) {
        light_threshold = val;
    }

    public static double getLightThreshold() {
        return light_threshold;
    }

    public static AlarmManager getAlarmMgr() {
        return alarmMgr;
    }

    public static List<SensorAlarm> getAlarms() {
        return alarms;
    }

}
