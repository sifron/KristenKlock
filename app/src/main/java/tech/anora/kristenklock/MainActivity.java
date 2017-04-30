package tech.anora.kristenklock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TimePicker time;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;
    private int alarmID = 0;
    public List<Date> timeList = new ArrayList<Date>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();
        alarmMgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmID", alarmID++);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        time = (TimePicker) findViewById(R.id.timePicker);
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

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        Date tempDate = new Date();
        tempDate.setTime(calendar.getTimeInMillis());
        timeList.add(tempDate);

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

    public void cancelAlarm() {
        alarmMgr.cancel(alarmIntent);
    }

    public void launchAlarmsList(View view)
    {
        Intent myIntent = new Intent(this, TimeList.class);
        this.startActivity(myIntent);
    }
}
