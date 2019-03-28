package stt.umc.feature.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.R;
import stt.umc.feature.Request.ClinicalMedicalTicketRequest;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Request.SubclinicalMedicalTicketRequest;
import stt.umc.feature.Request.TicketInformationRequest;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


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
        String testUrl = "https://fit-umc-stt.azurewebsites.net/patient/thongtinkhambenh/BN21677";
        StringBuilder ticketStrBuilder = getTicket(testUrl);
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
//        JSONObject jsonTicketClinical = null;
//        JSONObject jsonTicketSubclinical[] = null;
//        int subClinicalLength = 0;
//        try {
//            jsonTicketClinical = new JSONObject(convertJSONStringClinical(ticket));
//            List<String> subClinicalStringArr = convertJSONStringSubclinical(ticket);
//            subClinicalLength = subClinicalStringArr.size();
//            //int clinicallength = convertJSONStringSubclinical(ticket).length;
//            for (int i = 0; i < subClinicalLength; i++) {
//                jsonTicketSubclinical[i] = new JSONObject(subClinicalStringArr.get(i));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (jsonTicketClinical != null || jsonTicketSubclinical != null) {
//            ClinicalMedicalTicketRequest cmTicketRequest = null;
//            if (jsonTicketClinical != null) {
//                cmTicketRequest = new ClinicalMedicalTicketRequest(jsonTicketClinical);
//            }else {  cmTicketRequest =new ClinicalMedicalTicketRequest(null,null,null,null);}
//            String[] clsRoom = new String[subClinicalLength];
//            String[] clsName = new String[subClinicalLength];
//            Integer[] clsCurrentNumber = new Integer[subClinicalLength];
//            Integer[] clsYourNumber = new Integer[subClinicalLength];
//            String[] clsTime = new String[subClinicalLength];
//            SubclinicalMedicalTicketRequest scmTicketRequest[] = new SubclinicalMedicalTicketRequest[subClinicalLength];
//            for (int i = 0; i < subClinicalLength; i++) {
//                try {
//                    scmTicketRequest[i] = new SubclinicalMedicalTicketRequest(jsonTicketSubclinical[i]);
//                    clsRoom[i] = scmTicketRequest[i].getRoomID();
//                    clsYourNumber[i] = scmTicketRequest[i].getTicketNumber();
//                    clsCurrentNumber[i] = scmTicketRequest[i].getRoomCurrentNumber();
//                    clsTime[i] = scmTicketRequest[i].getExpectedTime();
//                    clsName[i] = scmTicketRequest[i].getFunctionalName();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            tvPhongKham.setText(cmTicketRequest.getRoomID());
//            tvCurrentNumber.setText(cmTicketRequest.getRoomCurrentNumber());
//            tvYourNumber.setText(cmTicketRequest.getTicketNumber());
//            tvTime.setText(cmTicketRequest.getExpectedTime());
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

    public String convertJSONStringClinical(final String mParamString) {
        final CountDownLatch latchClinical = new CountDownLatch(1);
        final List<String> transform1_3 = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] transform1 = mParamString.split("canLamSang");
                String[] transform1_2 = transform1[0].split("lamSang");
                transform1_3.add(transform1_2[1].substring(3, transform1_2[1].length() - 3));
                latchClinical.countDown();
            }
        }).start();
        try {
            latchClinical.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return transform1_3.get(0);
    }

    public List<String> convertJSONStringSubclinical(final String mParamString) {
        final CountDownLatch latchSubclinical = new CountDownLatch(1);
        final List<String> listStrings = new ArrayList<>();
        Thread threadSubclinical = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] transform2 = mParamString.split("canLamSang");
                String transform2_2 = transform2[1].substring(3, transform2[1].length() - 2);
                String[] transform2_3 = transform2_2.split("},");
                String[] transform2_4 = new String[transform2_3.length];
                for (int i = 0; i < transform2_3.length; i++) {
                    transform2_4[i] = transform2_3[i] + '}';
                    listStrings.add(transform2_4[i]);
                }
                latchSubclinical.countDown();
            }
        });
        threadSubclinical.start();
        try {
            latchSubclinical.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listStrings;
    }

    public static StringBuilder getTicket(final String urlString) {
        //PatientRunnable mPatientRunnable =  new PatientRunnable(urlString);
        //Thread mThread = new Thread(mPatientRunnable);
        //mThread.start();
        final CountDownLatch latch = new CountDownLatch(1);
        final StringBuilder roomRequest = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpsURLConnection mHttpsURLConnection = (HttpsURLConnection) url.openConnection();
                    mHttpsURLConnection.setDoInput(true);
                    mHttpsURLConnection.setRequestMethod("GET");
                    if (mHttpsURLConnection.getResponseCode() == 200) {
                        mHttpsURLConnection.connect();
                        InputStream responseBody = mHttpsURLConnection.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(responseBody));
                        String line;
                        while ((line = rd.readLine()) != null) {
                            roomRequest.append(line);
                        }
                        //JSONObject json = new JSONObject(sb.toString().substring(1,sb.length()-1));
                        //PatientRequest patientRequest = new PatientRequest(json);
                        mHttpsURLConnection.disconnect();
                        latch.countDown();
                    } else {
                        Log.d("Error ", "");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //return mPatientRunnable.getPatientRequest();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return roomRequest;
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
