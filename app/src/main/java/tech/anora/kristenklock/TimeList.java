package tech.anora.kristenklock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private ArrayAdapter<SensorAlarm> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list);

        // Find the ListView resource.
        alarmsListView = (ListView) findViewById( R.id.alarmsList );

        //listAdapter = new ArrayAdapter<String>(this, R.layout.alarm_list_item, R.id.timeText, alarmsList);
        //listAdapter = new ArrayAdapter<>(this, R.layout.alarm_list_item, alarmsList);


        alarmsListView.setAdapter(listAdapter);

/*        // Create and populate a List of planet names.
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        ArrayList<String> planetList = new ArrayList<>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, planetList);
*/
    }

    public void launchMainActivity(View view)
    {
//        Intent myIntent = new Intent(this, MainActivity.class);
//        this.startActivity(myIntent);
        finish();
    }
}
