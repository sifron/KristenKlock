package tech.anora.kristenklock;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;


/*
* Project Title: Kristen Klock
* Class: COMP590, Spring 2017
* Date: 5/2/17
* Authors: Sifron Benjamin and Collin Makohon
 */

public class SensorAlarm {
    private Calendar _calendar;
    private double _shake_thresh;
    private int _alarmID;
    private Context _context;
    private Intent _intent;
    private boolean _isOn;

    public SensorAlarm(Calendar calendar, double shake_thresh, int alarmID, Context context, Intent intent) {
        _calendar = calendar;
        _shake_thresh = shake_thresh;
        _alarmID = alarmID;
        _context = context;
        _intent = intent;
        _isOn = true;
    }

    public Calendar get_calendar() {
        return _calendar;
    }

    public void set_calendar(Calendar _calendar) {
        this._calendar = _calendar;
    }

    public double get_shake_thresh() {
        return _shake_thresh;
    }

    public void set_shake_thresh(double _shake_thresh) {
        this._shake_thresh = _shake_thresh;
    }

    public int get_alarmID() {
        return _alarmID;
    }

    public void turnOff() {
        Log.v("TAG", "Alarm " + this.get_alarmID() + " is turned off.");
        _isOn = false;
    }

    public void turnOn() {
        Log.v("TAG", "Alarm " + this.get_alarmID() + " is turned on.");
        _isOn = true;
    }

    public Context get_context() {
        return _context;
    }

    public Intent get_intent() {
        return _intent;
    }

    public void set_intent(Intent _intent) {
        this._intent = _intent;
    }

    public boolean isOn() {
        return _isOn;
    }
}
