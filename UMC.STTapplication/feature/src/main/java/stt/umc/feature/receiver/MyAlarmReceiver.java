package stt.umc.feature.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import stt.umc.feature.Home;
import stt.umc.feature.R;

import static stt.umc.feature.MyNotificationChannel.CHANNEL_ON_TIME;
import static stt.umc.feature.fragments.HomeFragment.notificationManager;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WakeLocker.acquire(context);
        SharedPreferences sharedPreferences;
        //Call set up notification
        sharedPreferences = context.getSharedPreferences("SETTING", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String time_out = sharedPreferences.getString("time_out","");
        String repeat = sharedPreferences.getString("repeat","");
        String ring_tone = sharedPreferences.getString("ring_tone_uri","");
        onSetUpNotification(Uri.parse(ring_tone),repeat,time_out,true,context);
    }

    //Set up notification when getting data
    public void onSetUpNotification(Uri ringTone,
                                    String repeat,
                                    String time_out,
                                    boolean vibrate,Context context) {
        //creating and assigning value to alarm manager class
        if (vibrate) {
            Intent activityIntent = new Intent(context, Home.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ON_TIME);
            notification.setSmallIcon(R.drawable.umc_logo)
                    .setContentIntent(contentIntent)
                    .setContentTitle("On Time")
                    .setContentText("Còn " + time_out+ " đến giờ khám")
                    .setVibrate(new long[]{1000, 1000, 1000,1000 ,1000})
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setAutoCancel(true)
                    .setSound(ringTone);
            notificationManager.notify(1, notification.build());
        }
        WakeLocker.release();
    }
}

