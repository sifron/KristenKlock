package tech.anora.kristenklock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class TimeList extends AppCompatActivity {

    private ListView alarmsListView ;
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list);

        // Find the ListView resource.
        alarmsListView = (ListView) findViewById( R.id.alarmsList );

        // Create and populate a List of planet names.
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        ArrayList<String> planetList = new ArrayList<>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, planetList);

        // Add more planets. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.
        listAdapter.add( "Ceres" );
        listAdapter.add( "Pluto" );
        listAdapter.add( "Haumea" );
        listAdapter.add( "Makemake" );
        listAdapter.add( "Eris" );
        listAdapter.add( "Pluto" );
        listAdapter.add( "Scott" );
        listAdapter.add( "Andromeda" );
        listAdapter.add( "Rae" );
        listAdapter.add( "Canto" );

        alarmsListView.setAdapter(listAdapter);
    }

    public void launchMainActivity(View view)
    {
//        Intent myIntent = new Intent(this, MainActivity.class);
//        this.startActivity(myIntent);
        finish();
    }
}
