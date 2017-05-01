package tech.anora.kristenklock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TimeList extends AppCompatActivity {

    private List<SensorAlarm> alarmsList = MainActivity.getAlarms();
    private ListView alarmsListView ;
    private ArrayAdapter<String> listAdapter;
    private List<String> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list);

        times = new ArrayList<>();
        for(SensorAlarm s : alarmsList) {
            times.add(setTimeString(s.get_calendar()));
            Log.v("TAG", setTimeString(s.get_calendar()));
        }


        // Find the ListView resource.
        alarmsListView = (ListView) findViewById( R.id.alarmsList );

       // listAdapter = new ArrayAdapter<>(this, R.layout.alarm_list_item, R.id.timeText, times);
        //listAdapter.add("");

        AlarmListAdapter alarmAdapter = new AlarmListAdapter(alarmsList, this);

        alarmsListView.setAdapter(alarmAdapter);
        //alarmsListView.setAdapter(listAdapter);

    }

    public void launchMainActivity(View view)
    {
//        Intent myIntent = new Intent(this, MainActivity.class);
//        this.startActivity(myIntent);
        finish();
    }

    protected String setTimeString(Calendar alarmCal)
    {
        //Convert time to 12 hour time for toast
        int timeHour;
        String timeMin = Integer.toString(alarmCal.get(Calendar.MINUTE));
        String amOrPm;
        if (alarmCal.get(Calendar.HOUR_OF_DAY) > 12)
        {
            timeHour = alarmCal.get(Calendar.HOUR_OF_DAY) % 12;
            amOrPm = "PM";
        }
        else
        {
            timeHour = alarmCal.get(Calendar.HOUR_OF_DAY);
            amOrPm = "AM";
        }

        if(alarmCal.get(Calendar.MINUTE) < 10)
            timeMin = "0" + timeMin;

        String timeString = timeHour + ":" + timeMin + " " + amOrPm;

        return timeString;
    }
}
