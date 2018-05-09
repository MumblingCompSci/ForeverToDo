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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.services.concurrency.Priority;

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
    private static final String EXTRA_SOUND_ON = "sound on";
    private static final String EXTRA_NOTIFICATION_ON = "notifications on";
    private static final String EXTRA_NOTIFICATION_TIME = "notification time";
    private static boolean mSoundOn;
    private static boolean mNotificationsOn;
    private static int mNotificationTime;

    public static Intent newIntent(Context context) {
       return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn, boolean soundOn, boolean notificationsOn, int notificationTime) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        mSoundOn = soundOn;
        mNotificationsOn = notificationsOn;
        mNotificationTime = notificationTime;
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
        Log.i(TAG, "Handling intent");
        if (!isNetworkAvailableAndConnected()) {
            return;
        }

        initChannel(this);
        Resources resources = getResources();
        Intent i = TaskListActivity.newIntent(this);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

        // Grab list of tasks from database


        // Grab priority preferences for the notifications


        // Compare current date and time to task's due date taking in account notification preferences

        // If they are equal, set the notification builder and push notification
        // The following lines are the setup for the notifications
        final Calendar cal = Calendar.getInstance();
        int waitTime = 0;
        switch(mNotificationTime){
            case 0:
                waitTime = 10;
                break;
            case 1:
                waitTime = 30;
                break;
            case 2:
                waitTime = 60;
                break;
            case 3:
                waitTime = 300;
                break;
            case 4:
                waitTime = 1440;
                break;
            case 5:
                waitTime = 2880;
        }
        List<ToDoTask> tasks = ToDoTaskBank.get(this).getToDoTasks();
        for(int j = 0; j < tasks.size(); ++j) {
            if ((tasks.get(j).getDueDate().getTime() / 60000) - (cal.getTime().getTime() / 60000) < waitTime) {
                Log.i(TAG, "Setting a notification!");
                if (mNotificationsOn && tasks.get(j).getCompleteDate().getTime() != 0) {
                    NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this);
                    notifBuilder.setTicker("Finish your task!")
                            .setSmallIcon(android.R.drawable.ic_menu_agenda)
                            .setContentTitle(tasks.get(j).getTitle() + " is due!!!")
                            .setContentText(tasks.get(j).getTitle() + " is due!!!")
                            .setContentIntent(pi)
                            .setAutoCancel(true);
                    if (mSoundOn) {
                        notifBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                    } else {
                        notifBuilder.setPriority(NotificationCompat.PRIORITY_LOW);
                    }
                    NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
                    notifBuilder.setContentText("Finish your task!");
                    notifManager.notify(0, notifBuilder.build());
                }
            }
        }


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
