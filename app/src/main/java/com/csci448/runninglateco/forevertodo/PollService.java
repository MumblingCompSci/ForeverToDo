package com.csci448.runninglateco.forevertodo;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Created by Clara on 5/8/2018.
 */

public class PollService extends IntentService {
    private static final String TAG = "PollService";

    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1);
    public static final String ACTION_SHOW_NOTIFICATION = "com.csci448.claratran.locatr.SHOW_NOTIFICATION";
    public static final String PERM_PRIVATE = "com.csci448.claratran.locatr.PRIVATE";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }

        QueryPreferences.setAlarmOn(context, isOn);
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }

        String lastResultId = QueryPreferences.getLastResultId(this);
        Resources resources = getResources();
        Intent i = TaskListActivity.newIntent(this);

        // Grab list of tasks from database


        // Grab priority preferences for the notifications


        // Compare current date and time to task's due date taking in account notification preferences


        // If they are equal, set the notification builder and push notification
        // The following lines are the setup for the notifications
        /**
         *  NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
         *      .setTicker(resources.getString(R.string.new_pictures_title))
         *      .setSmallIcon(android.R.drawable.ic_menu_report_image)
         *      .setContentTitle(resources.getString(R.string.new_pictures_title))
         *      .setContentText(resources.getString(R.string.new_pictures_text))
         *      .setContentIntent(pi)
         *      .setAutoCancel(true);
         * NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
         * notifBuilder.setContentText(resources.getString(<add string value));
         * notifManager.notify(0, notifBuilder.build());
         */


    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }

    public void initChannel(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }

        NotificationManager notifManager = (NotificationManager) context. getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(
                "default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notifManager.createNotificationChannel(channel);
    }
}
