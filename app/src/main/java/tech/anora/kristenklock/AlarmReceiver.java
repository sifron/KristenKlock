package tech.anora.kristenklock;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by cmakohon on 4/20/17.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    Context _context;
    @Override
    public void onReceive(Context context, Intent intent) {

        _context = context;
        Log.v("TAG", "Alarm has gone off!");

        Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(context, u);
        r.play();

        showNotifications();

    }

    public void showNotifications()
    {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(_context)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                        .setContentTitle("Kristen Klock")
                        .setContentText("Time to wake up!");


// Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

    }

}
