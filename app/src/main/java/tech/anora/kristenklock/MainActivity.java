package tech.anora.kristenklock;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker time;
    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;
    private Context context;
    private int alarmID = 0;
    CalibrationDialog prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();
        time = (TimePicker) findViewById(R.id.timePicker);
        prompt = new CalibrationDialog();
        prompt.show(getSupportFragmentManager(), "calibrate");

    }

    public void setAlarm(View v) {
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

        alarmMgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmID", alarmID);
        alarmIntent = PendingIntent.getBroadcast(context, alarmID++, intent, 0);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        Context context = getApplicationContext();
        showSetToast(hour,minute);

        Log.v("TAG", "Hour: " + hour + ", Minute: " + minute);
        Log.v("TAG", "" + calendar.getTimeInMillis());

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
        toast.show();
    }

    public static void cancelAlarm(Context context, Intent intent, int intentID) {
        alarmMgr.cancel(PendingIntent.getBroadcast(context, intentID, intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
