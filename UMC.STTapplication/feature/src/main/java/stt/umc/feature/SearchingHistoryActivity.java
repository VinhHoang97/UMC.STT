package stt.umc.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Hashtable;

public class SearchingHistoryActivity extends AppCompatActivity {

    private String[] nameVictimHistory = {"Huỳnh Thị Lưu", "Cao Văn Sang"};
    private String[] scanTime = {"Tháng 2 năm 2019", "Tháng 3 năm 2019"};
    private Integer[] oldVictimHistory ={45,15};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_history);
        ListView listView =findViewById(R.id.historyLv);
        CustomHistoryListViewAdapter customHistoryListViewAdapter = new CustomHistoryListViewAdapter(this,nameVictimHistory,scanTime,oldVictimHistory);
        listView.setAdapter(customHistoryListViewAdapter);
    }
}
