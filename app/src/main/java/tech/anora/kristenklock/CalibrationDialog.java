package tech.anora.kristenklock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

/**
 * Created by cmakohon on 5/1/17.
 */

public class CalibrationDialog extends DialogFragment implements SensorEventListener {

    private SensorManager sm;
    private Sensor lightSensor;
    private List<Sensor> l;
    private double light_threshold;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        sm = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        l = sm.getSensorList(Sensor.TYPE_ALL);

        if(sm.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.calibration_title)
                .setMessage(R.string.calibration_body)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.setLightThreshhold(light_threshold);
                        sm.unregisterListener(CalibrationDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        sm.unregisterListener(this, lightSensor);
    }

    @Override
    public void onStart() {
        super.onStart();
        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        light_threshold = event.values[0];
        //Log.v("TAG", "" + light_threshold);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
