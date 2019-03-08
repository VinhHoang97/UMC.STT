package stt.umc.feature;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomHistoryListViewAdapter extends BaseAdapter {
    private Context context;
    private String[] nameVictimHistory;
    private String[] scanTime ;
    private Integer[] oldVictimHistory;

    public CustomHistoryListViewAdapter(Context context, String[] nameVictimHistory, String[] scanTime, Integer[] oldVictimHistory) {
        this.context = context;
        this.nameVictimHistory = nameVictimHistory;
        this.scanTime = scanTime;
        this.oldVictimHistory = oldVictimHistory;
    }

    @Override
    public int getCount() {
        return scanTime.length;
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
        convertView = layoutInflater.inflate(R.layout.listviewhistory_item,null);
        TextView scanTimeTv = convertView.findViewById(R.id.scanTime);
        TextView nameVictimTv = convertView.findViewById(R.id.nameVictimHistory);
        TextView oldVictimTv = convertView.findViewById(R.id.oldVictimHistory);

        scanTimeTv.setText(scanTime[position]);
        nameVictimTv.setText(nameVictimHistory[position]);
        oldVictimTv.setText(oldVictimHistory[position].toString() + " tuổi");
        return convertView;
    }
}
