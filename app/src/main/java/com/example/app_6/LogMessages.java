package com.example.app_6;

public class LogMessages {

    private LogMessages(){}

    public static final String SERVICE_CREATED = "Service created";
    public static final String FOREGROUND_SERVICE_STARTED = "Foreground service started with initial notification";
    public static final String SERVICE_ON_START_COMMAND = "Service onStartCommand called";
    public static final String SERVICE_DESTROYED = "Service destroyed and handler callbacks removed";
    public static final String NOTIFICATION_UPDATED = "Notification updated with current time: ";
    public static final String NOTIFICATION_MANAGER_NULL = "NotificationManager is null, cannot update notification";
    public static final String NOTIFICATION_CHANNEL_CREATED = "Notification channel created";
    public static final String NOTIFICATION_CHANNEL_MANAGER_NULL = "NotificationManager is null, cannot create notification channel";
}
