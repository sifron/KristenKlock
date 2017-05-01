package tech.anora.kristenklock;

import java.util.Calendar;


/**
 * Created by cmakohon on 5/1/17.
 */

public class SensorAlarm {
    private Calendar _calendar;
    private double _shake_thresh;
    private String _name;

    public SensorAlarm(Calendar calendar, double shake_thresh, String name) {
        _calendar = calendar;
        _shake_thresh = shake_thresh;
        _name = name;
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

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
