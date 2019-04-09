package stt.umc.feature.CustomView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

import stt.umc.feature.R;
import stt.umc.feature.receiver.MyAlarmReceiver;

public class CustomCLSGridViewAdapter extends BaseAdapter {
    private Context context;
    private String[] clsRoom;
    private String[] clsName;
    private Integer[] clsCurrentNumber;
    private Integer[] clsYourNumber;
    private String[] clsTime;

    public CustomCLSGridViewAdapter(Context context, String[] clsRoom, String[] clsName, Integer[] clsCurrentNumber, Integer[] clsYourNumber, String[] clsTime) {
        this.context = context;
        this.clsRoom = clsRoom;
        this.clsName = clsName;
        this.clsCurrentNumber = clsCurrentNumber;
        this.clsYourNumber = clsYourNumber;
        this.clsTime = clsTime;
    }

    @Override
    public int getCount() {
        return clsRoom.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.gridviewhome,null);
        TextView tvClsName = (TextView)convertView.findViewById(R.id.clsName);
        TextView tvClsRoom = (TextView)convertView.findViewById(R.id.clsRoom);
        TextView tvClsTime = (TextView)convertView.findViewById(R.id.clsTime);
        TextView tvClsCurrentNum = (TextView)convertView.findViewById(R.id.clsCurrentNumber);
        TextView tvClsYourNum = (TextView)convertView.findViewById(R.id.clsYourNumber);
        TextView tvClsWaitingTime = (TextView)convertView.findViewById(R.id.waitClsTime);
        tvClsName.setText(clsName[position]);
        tvClsRoom.setText(clsRoom[position]);
        tvClsTime.setText(clsTime[position]);
        tvClsCurrentNum.setText(clsCurrentNumber[position].toString());
        tvClsYourNum.setText(clsYourNumber[position].toString());

        SharedPreferences sharedPreferences;
        //Call set up notification
        sharedPreferences = context.getSharedPreferences("SETTING", Context.MODE_PRIVATE);
        String time_out = sharedPreferences.getString("time_out", "0");

        String[] mSplitString = clsTime[position].split(":");

        Calendar calendarCurrent = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mSplitString[0]));
        //calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, Integer.parseInt(mSplitString[1]));
        //calendar.set(Calendar.MINUTE, 22);

        long longMilli = 0;
        long longMinute = 0;
        long longHour = 0;
        String waitTimeText;
        if (calendarCurrent.compareTo(calendar) <= 0) {
            longMilli = calendarCurrent.getTimeInMillis() - calendar.getTimeInMillis();
            longMinute = (longMilli/1000)/60;
            longHour = longMinute/60;
            longMinute = longMinute%60;
            waitTimeText= String.valueOf(longHour)+" giờ "+String.valueOf(longMinute) +" phút tới giờ khám";
            Intent notifyIntent = new Intent(context, MyAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (context, 100, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - Integer.parseInt(time_out.split(" ")[0]) * 60 * 1000
                    , pendingIntent);
        } else {
            waitTimeText = "Đã quá giờ khám";
        }
        tvClsWaitingTime.setText(waitTimeText);

        return convertView;
    }
}
