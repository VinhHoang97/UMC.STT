package stt.umc.feature.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.R;
import stt.umc.feature.Request.ClinicalMedicalTicketRequest;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Request.SubclinicalMedicalTicketRequest;
import stt.umc.feature.Request.TicketInformationRequest;
import stt.umc.feature.Utils.GlobalUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        GridView gridView = view.findViewById(R.id.homeGrid);
        TextView tvPhongKham = view.findViewById(R.id.idPhongKham);
        TextView tvCurrentNumber = view.findViewById(R.id.currentNumber);
        TextView tvTime = view.findViewById(R.id.idThoiGianDuKien);
        TextView tvYourNumber = view.findViewById(R.id.yourNumber);
        TextView tvRemainingTime = view.findViewById(R.id.waitTime);
        Bundle bundle = this.getArguments();
        String sb = bundle.getString("patient");
        JSONObject json = null;
        try {
            json = new JSONObject(sb.substring(1, sb.length() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PatientRequest patientRequest = new PatientRequest(json);
        //set profile view
        String url = "https://fit-umc-stt.azurewebsites.net/patient/thongtinkhambenh/" + patientRequest.getPatientID();
        //String testUrl = "https://fit-umc-stt.azurewebsites.net/patient/thongtinkhambenh/BN21677";
        StringBuilder ticketStrBuilder = GlobalUtils.getTicket(url);
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
            clsTime[i] = informationRequest.getCangLamSang().get(i).getExpectedTime().substring(11, 16);
            clsName[i] = informationRequest.getCangLamSang().get(i).getFunctionalName();
        }
        tvPhongKham.setText(cmTicketRequest.getRoomID());
        tvCurrentNumber.setText(cmTicketRequest.getRoomCurrentNumber().toString());
        tvYourNumber.setText(cmTicketRequest.getTicketNumber().toString());
        tvTime.setText(cmTicketRequest.getExpectedTime().substring(11, 16));
        String sDate1= cmTicketRequest.getExpectedTime();
        ParsePosition pp1 = new ParsePosition(0);
        Date then=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1,pp1);
        Date now = new Date();
        String remaining1 = DateUtils.formatElapsedTime ((then.getTime() - now.getTime())/1000);
        Log.d(remaining1,"");
        tvRemainingTime.setText(
                remaining1);
        CustomCLSGridViewAdapter customCLSGridViewAdapter = new CustomCLSGridViewAdapter(view.getContext(),
                clsRoom,
                clsName,
                clsCurrentNumber,
                clsYourNumber,
                clsTime);
        gridView.setAdapter(customCLSGridViewAdapter);
        //}
        // Inflate the layout for this fragment
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
