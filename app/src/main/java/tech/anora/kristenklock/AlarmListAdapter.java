package tech.anora.kristenklock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static tech.anora.kristenklock.R.id.alarmsList;

/**
 * Created by Sifron on 5/1/2017.
 */

public class AlarmListAdapter extends ArrayAdapter<SensorAlarm> {

    public AlarmListAdapter(Context context, ArrayList<SensorAlarm> alarms)
    {
        super(context, 0, alarms);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        final SensorAlarm alarm = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item, parent, false);
        }
        // Lookup view for data population
        TextView timeTestView = (TextView) convertView.findViewById(R.id.timeText);

        // Populate the data into the template view using the data object
        timeTestView.setText(setTimeString(alarm.get_calendar()));

        FloatingActionButton deleteButton = (FloatingActionButton) convertView.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                SensorAlarm alarmtoDelete = getItem(position);
                MainActivity.getAlarms().remove(alarmtoDelete);
                MainActivity.getAlarmMgr().cancel(PendingIntent.getBroadcast(alarmtoDelete.get_context(), alarmtoDelete.get_alarmID(),
                        alarmtoDelete.get_intent(), PendingIntent.FLAG_UPDATE_CURRENT));
                notifyDataSetChanged();
            }
        });

        Switch toggle = (Switch) convertView.findViewById(R.id.timeSwitch);
        toggle.setTag(position);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                SensorAlarm alarmtoToggle = getItem(position);
                if(alarmtoToggle.isOn()) {
                    alarmtoToggle.turnOff();
                    MainActivity.getAlarmMgr().cancel(PendingIntent.getBroadcast(alarmtoToggle.get_context(), alarmtoToggle.get_alarmID(),
                            alarmtoToggle.get_intent(), PendingIntent.FLAG_UPDATE_CURRENT));
                } else {
                    alarmtoToggle.turnOn();
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(alarmtoToggle.get_context(), alarmtoToggle.get_alarmID(),
                            alarmtoToggle.get_intent(), 0);

                    Calendar calendar = alarmtoToggle.get_calendar();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, alarmtoToggle.get_calendar().get(Calendar.HOUR_OF_DAY));
                    calendar.set(Calendar.MINUTE, alarmtoToggle.get_calendar().get(Calendar.MINUTE));

                    //if the alarm is set for a time that has already passed in the
                    //current day, set it for the next day
                    if(System.currentTimeMillis() >= calendar.getTimeInMillis()) {
                        calendar.add(Calendar.DATE, 1);
                    }

                    MainActivity.getAlarmMgr().set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
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
