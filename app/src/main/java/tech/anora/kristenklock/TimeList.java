package tech.anora.kristenklock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class TimeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list);

        ArrayList<SensorAlarm> sensorsArray = (ArrayList<SensorAlarm>)MainActivity.getAlarms();
        AlarmListAdapter adapter = new AlarmListAdapter(this, sensorsArray);

        ListView listView = (ListView) findViewById(R.id.alarmsList);
        listView.setAdapter(adapter);

    }

    public void launchMainActivity(View view)
    {
        finish();
    }

}
