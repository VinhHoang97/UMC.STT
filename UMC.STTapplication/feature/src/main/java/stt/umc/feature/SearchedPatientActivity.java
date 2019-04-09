package stt.umc.feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.Request.ClinicalMedicalTicketRequest;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Request.TicketInformationRequest;
import stt.umc.feature.Utils.ConnectionAsync;
import stt.umc.feature.Utils.GlobalUtils;

import static stt.umc.feature.Utils.GlobalUtils.getTicket;

public class SearchedPatientActivity extends AppCompatActivity {
    public static SearchedPatientActivity searchedPatientActivity;
    private GridView gridView;
    private TextView searchPatientName;
    private TextView searchedPatientBirthday;
    private TextView remaingAppointment;
    private TextView tvPhongKham;
    private TextView tvCurrentNumber;
    private TextView tvTime;
    private TextView tvYourNumber;
    private View mLoadingProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        gridView = findViewById(R.id.searchPatientGridview);
        tvPhongKham = findViewById(R.id.searchedPatientRoom);
        tvCurrentNumber = findViewById(R.id.searchedPatientCurrNumber);
        tvTime = findViewById(R.id.searchedPatientExpectedTime);
        tvYourNumber = findViewById(R.id.searchedPatientNumber);
        searchPatientName = findViewById(R.id.searchedPatientName);
        searchedPatientBirthday = findViewById(R.id.searchedPatientBirthday);
        remaingAppointment = findViewById(R.id.remaingAppointment);
        mLoadingProgress = findViewById(R.id.searchLoadingPanel);
        searchedPatientActivity = this;
    }

    public void onReceivingData(String data) {
        JSONObject json = null;
        try {
            json = new JSONObject(data.substring(1, data.length() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PatientRequest patientRequest = new PatientRequest(json);
        searchPatientName.setText(patientRequest.getLastName() + " "+patientRequest.getMiddleName()+" "+patientRequest.getFirstName());
        String birthday[] = patientRequest.getPatientBirthday().substring(0,10).split("-");
        searchedPatientBirthday.setText(birthday[2]+"/"+birthday[1]+"/"+birthday[0]);

        String url = "https://fit-umc-stt.azurewebsites.net/clinic/thongtinkhambenh/" + patientRequest.getPatientID();
        GlobalUtils.getPatientHttpMethod(url, new ConnectionAsync.httpRequestListener() {
            @Override
            public void onRecevie(String data) {
                String ticket =data;
                JSONObject jsonTicket = null;
                try {
                    jsonTicket = new JSONObject(ticket);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TicketInformationRequest informationRequest = new TicketInformationRequest(jsonTicket);
                Integer remain= (informationRequest.getLamSang().size()+informationRequest.getCangLamSang().size());
                remaingAppointment.setText(remain.toString());
                ClinicalMedicalTicketRequest cmTicketRequest = informationRequest.getLamSang().get(0);
                String[] clsRoom = new String[informationRequest.getCangLamSang().size()];
                String[] clsName = new String[informationRequest.getCangLamSang().size()];
                Integer[] clsCurrentNumber = new Integer[informationRequest.getCangLamSang().size()];
                Integer[] clsYourNumber = new Integer[informationRequest.getCangLamSang().size()];
                String[] clsTime = new String[informationRequest.getCangLamSang().size()];

                for (int i = 0; i < informationRequest.getCangLamSang().size(); i++) {
                    clsRoom[i] = informationRequest.getCangLamSang().get(i).getRoomID();
                    clsYourNumber[i] = informationRequest.getCangLamSang().get(i).getTicketNumber();
                    clsCurrentNumber[i] = informationRequest.getCangLamSang().get(i).getRoomCurrentNumber();
                    clsTime[i] = informationRequest.getCangLamSang().get(i).getExpectedTime();
                    clsName[i] = informationRequest.getCangLamSang().get(i).getFunctionalName();
                }
                tvPhongKham.setText(cmTicketRequest.getRoomID());
                tvCurrentNumber.setText(cmTicketRequest.getRoomCurrentNumber().toString());
                tvYourNumber.setText(cmTicketRequest.getTicketNumber().toString());
                tvTime.setText(cmTicketRequest.getExpectedTime());
                CustomCLSGridViewAdapter customCLSGridViewAdapter = new CustomCLSGridViewAdapter(getApplicationContext(),
                        clsRoom,
                        clsName,
                        clsCurrentNumber,
                        clsYourNumber,
                        clsTime);
                gridView.setAdapter(customCLSGridViewAdapter);
                onLoadingDataComplete(true);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public void onFailData() {
        //Do something
    }

    public void onLoadingDataComplete(final boolean completed) {
        if (!completed) {
            gridView.setVisibility(View.INVISIBLE);
            mLoadingProgress.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.VISIBLE);
            mLoadingProgress.setVisibility(View.INVISIBLE);
        }
    }
}
