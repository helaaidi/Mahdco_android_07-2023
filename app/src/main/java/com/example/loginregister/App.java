package com.example.loginregister;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "exampleChannel";
    public static final String CHANNEL_ID2 = "exampleChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_ID2,
                    "Example Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channel2);
        }
    }
}