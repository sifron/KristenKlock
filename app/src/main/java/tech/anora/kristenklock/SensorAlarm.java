package tech.anora.kristenklock;

import java.util.Calendar;


/**
 * Created by cmakohon on 5/1/17.
 */

public class SensorAlarm {
    private Calendar _calendar;
    private double _shake_thresh;
    private int _alarmID;
    private boolean _isOn;

    public SensorAlarm(Calendar calendar, double shake_thresh, int alarmID) {
        _calendar = calendar;
        _shake_thresh = shake_thresh;
        _alarmID = alarmID;
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
        _isOn = false;
    }

    public void turnOn() {
        _isOn = true;
    }

}
