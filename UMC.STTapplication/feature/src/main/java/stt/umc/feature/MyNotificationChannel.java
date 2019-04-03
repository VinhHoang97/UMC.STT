package stt.umc.feature;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyNotificationChannel extends Application {
    public static final String CHANNEL_ON_TIME = "ontime";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ON_TIME,
                    "On Time Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is on time Channel")  ;
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
