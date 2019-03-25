package stt.umc.feature;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class MyAlarmReceiver extends BroadcastReceiver {
    Vibrator v;
    Context ct;
    String title;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        ct = context;
        Log.e("onReceive", "");
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);

        int badgeCount = 1;


        Bundle bundle = intent.getExtras();
        try {
            title = intent.getExtras().get("title").toString();
            // title = intent.getStringExtra("title");
            Toast.makeText(context, title, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        if (!Utlis.checkNetworkConnection(context)) {
        //
        //            Notification(context, "Wifi Connection off");
        //
        //        } else {
        Notification(context, "Please  pray for this prayer ");

    }




    public void Notification(Context context, String message) {
        // Set Notification Title
        String strtitle = "iPray  Prayer Reminder";
        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, Home.class);
        // Send data to NotificationView Class
        intent.putExtra("title", title);
        intent.putExtra("text", title);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.app_icon, "Previous", pIntent).build();
        // Create Notification using NotificationCompat.Builder

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                // Set Icon
                .setSmallIcon(R.drawable.umc_logo)
                // Set Ticker Message
                .setTicker(message)
                // Set Title
                .setContentTitle(context.getString(R.string.app_name))
                // Set Text
                .setContentText("Còn " )
                // Add an Action Button below Notification
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(38, builder.build());

    }
}

