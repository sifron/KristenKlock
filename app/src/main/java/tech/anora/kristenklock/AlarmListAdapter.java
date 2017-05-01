package tech.anora.kristenklock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sifron on 5/1/2017.
 */

public class AlarmListAdapter extends BaseAdapter implements ListAdapter {

    private List<SensorAlarm> alarmsList = MainActivity.getAlarms();
    private Context context;

    public AlarmListAdapter(List<SensorAlarm> list, Context context)
    {
        this.alarmsList = list;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return alarmsList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return alarmsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return alarmsList.get(position).get_alarmID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.alarm_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.timeText);

        listItemText.setText(setTimeString(alarmsList.get(position).get_calendar()));

        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //do something
                alarmsList.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        Switch toggleSwitch = (Switch)view.findViewById(R.id.timeSwitch);
        toggleSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //This is where we can toggle the alarm
                notifyDataSetChanged();
            }
        });

        return view;
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

        String timeString = "Alarm set for " + timeHour + ":" + timeMin + " " + amOrPm;

        return timeString;
    }
}
