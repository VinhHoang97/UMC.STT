package stt.umc.feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.Request.ClinicalMedicalTicketRequest;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Request.TicketInformationRequest;
import stt.umc.feature.Utils.GlobalUtils;

import static stt.umc.feature.Utils.GlobalUtils.getTicket;

public class SearchedPatientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridView gridView = findViewById(R.id.searchPatientGridview);
        TextView tvPhongKham = findViewById(R.id.searchedPatientRoom);
        TextView tvCurrentNumber = findViewById(R.id.searchedPatientCurrNumber);
        TextView tvTime = findViewById(R.id.searchedPatientExpectedTime);
        TextView tvYourNumber = findViewById(R.id.searchedPatientNumber);
        setContentView(R.layout.search_detail);
        String sb = getIntent().getStringExtra("searched_patient");
        String urlString = "https://fit-umc-stt.azurewebsites.net/patient/" + sb;
        StringBuilder searchedPatient= GlobalUtils.getPatientHttpMethod(urlString);
        JSONObject json = null;
        try {
            json = new JSONObject(searchedPatient.substring(1,sb.length()-1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PatientRequest patientRequest = new PatientRequest(json);
        String url = "https://fit-umc-stt.azurewebsites.net/patient/thongtinkhambenh/" + patientRequest.getPatientID();
        StringBuilder ticketStrBuilder = getTicket(url);
        String ticket = new String(ticketStrBuilder);
        JSONObject jsonTicket = null;
        try {
            jsonTicket = new JSONObject(ticket);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TicketInformationRequest informationRequest = new TicketInformationRequest(jsonTicket);
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
            clsTime[i] = informationRequest.getCangLamSang().get(i).getExpectedTime().substring(11,16);
            clsName[i] = informationRequest.getCangLamSang().get(i).getFunctionalName();
        }
        tvPhongKham.setText(cmTicketRequest.getRoomID());
        tvCurrentNumber.setText(cmTicketRequest.getRoomCurrentNumber().toString());
        tvYourNumber.setText(cmTicketRequest.getTicketNumber().toString());
        tvTime.setText(cmTicketRequest.getExpectedTime().substring(11,16));
        CustomCLSGridViewAdapter customCLSGridViewAdapter = new CustomCLSGridViewAdapter(this,
                clsRoom,
                clsName,
                clsCurrentNumber,
                clsYourNumber,
                clsTime);
        gridView.setAdapter(customCLSGridViewAdapter);
    }
}
