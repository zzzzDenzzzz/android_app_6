package com.example.app_6;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeService extends Service {

    private static final String TAG = "TimeService";
    private static final String CHANNEL_ID = "TimeServiceChannel";
    private static final int NOTIFICATION_ID = 1;
    private static final int UPDATE_INTERVAL_MS = 5000;

    private Handler handler;
    private Runnable runnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Log.d(TAG, LogMessages.SERVICE_CREATED);

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                updateNotification();
                handler.postDelayed(this, UPDATE_INTERVAL_MS);
            }
        };
        handler.post(runnable);

        startForeground(NOTIFICATION_ID, createNotification(StringConstants.INITIALIZING));
        Log.d(TAG, LogMessages.FOREGROUND_SERVICE_STARTED);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, LogMessages.SERVICE_ON_START_COMMAND);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        Log.d(TAG, LogMessages.SERVICE_DESTROYED);
    }

    private void updateNotification() {
        String currentTime = new SimpleDateFormat(StringConstants.TIME_FORMAT_PATTERN, Locale.getDefault()).format(new Date());
        Notification notification = createNotification(currentTime);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
            Log.d(TAG, LogMessages.NOTIFICATION_UPDATED + currentTime);
        } else {
            Log.e(TAG, LogMessages.NOTIFICATION_MANAGER_NULL);
        }
    }

    private Notification createNotification(String currentTime) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(StringConstants.CURRENT_TIME)
                .setContentText(currentTime)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    @SuppressLint("ObsoleteSdkInt")
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    StringConstants.TIME_SERVICE_CHANNEL,
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
                Log.d(TAG, LogMessages.NOTIFICATION_CHANNEL_CREATED);
            } else {
                Log.e(TAG, LogMessages.NOTIFICATION_CHANNEL_MANAGER_NULL);
            }
        }
    }
}
