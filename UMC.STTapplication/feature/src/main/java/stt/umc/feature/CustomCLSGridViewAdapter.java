package stt.umc.feature;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        convertView=layoutInflater.inflate(R.layout.gridviewhome,null);
        TextView tvClsName = (TextView)convertView.findViewById(R.id.clsName);
        TextView tvClsRoom = (TextView)convertView.findViewById(R.id.clsRoom);
        TextView tvClsTime = (TextView)convertView.findViewById(R.id.clsTime);
        TextView tvClsCurrentNum = (TextView)convertView.findViewById(R.id.clsCurrentNumber);
        TextView tvClsYourNum = (TextView)convertView.findViewById(R.id.clsYourNumber);

        tvClsName.setText(clsName[position]);
        tvClsRoom.setText(clsRoom[position]);
        tvClsTime.setText(clsTime[position]);
        tvClsCurrentNum.setText(clsCurrentNumber[position].toString());
        tvClsYourNum.setText(clsYourNumber[position].toString());

        return convertView;
    }
}
