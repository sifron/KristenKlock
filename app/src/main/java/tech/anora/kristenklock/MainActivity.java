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

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker time;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();
        alarmMgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
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

        Log.v("TAG", "Hour: " + hour + ", Minute: " + minute);
        Log.v("TAG", "" + calendar.getTimeInMillis());
    }
}
