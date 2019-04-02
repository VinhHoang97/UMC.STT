package stt.umc.feature.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import stt.umc.feature.Home;
import stt.umc.feature.R;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int PERIOD = 300000;  // 5 minutes

    @Override
    public void onReceive(Context context, Intent intent) {   //Build pending intent from calling information to display Notification
        PendingIntent Sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        NotificationManager manager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        Notification.Builder noti = new Notification.Builder(context);
        noti.setContentIntent(Sender);

        noti.setAutoCancel(false);
        noti.setTicker("this is ticker text");
        noti.setContentTitle("WhatsApp Notification");
        noti.setContentText("You have a new message");
        noti.setSmallIcon(R.drawable.umc_logo);
        noti.setContentIntent(Sender);
        noti.setOngoing(true);
        noti.setSubText("This is subtext...");   //API level 16
        noti.setNumber(100);
        noti.build();
        manager.notify(R.string.app_name, noti.getNotification());

        //intent to call the activity which shows on ringing
        Intent myIntent = new Intent(context, Home.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);

        //display that alarm is ringing
        Toast.makeText(context, "Alarm Ringing...!!!", Toast.LENGTH_LONG).show();
    }
}
