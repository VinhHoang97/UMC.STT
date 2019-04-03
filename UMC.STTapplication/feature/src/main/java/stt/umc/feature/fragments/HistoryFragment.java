package stt.umc.feature.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.CustomView.CustomHistoryListViewAdapter;
import stt.umc.feature.R;
import stt.umc.feature.Request.ClinicalMedicalTicketRequest;
import stt.umc.feature.Request.HistoryRequest;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Request.TicketInformationRequest;

import static stt.umc.feature.Utils.GlobalUtils.getHistory;
import static stt.umc.feature.Utils.GlobalUtils.getTicket;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listView = view.findViewById(R.id.historyLv);

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
        void onFragmentInteraction(Uri uri);
    }

    public void onReceivingData(String data){
        String sb = data;
        JSONObject json = null;
        try {
            json = new JSONObject(sb.substring(1, sb.length() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PatientRequest patientRequest;
        patientRequest = new PatientRequest(json);


        //
        //set profile view
        String url = "https://fit-umc-stt.azurewebsites.net/history/" + patientRequest.getPatientID();
        //String testUrl = "https://fit-umc-stt.azurewebsites.net/patient/thongtinkhambenh/BN21677";
        StringBuilder historyBuilder = getHistory(url);
        String history = new String(historyBuilder);
        JSONArray jsonHistory = null;
        try {
            jsonHistory = new JSONArray(history);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] nameVictimHistory = new String[jsonHistory.length()];
        String[] scanTime =new String[jsonHistory.length()];
        Integer[] ageVictimHistory =new Integer[jsonHistory.length()];
        for (int i =0;i<jsonHistory.length();i++){
            try {
                HistoryRequest historyRequest = new HistoryRequest(jsonHistory.getJSONObject(i));
                nameVictimHistory[i] = historyRequest.getHoTen();
                scanTime[i] = historyRequest.getIdHistory();
                ageVictimHistory[i] = historyRequest.getTuoi();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        CustomHistoryListViewAdapter customHistoryListViewAdapter = new CustomHistoryListViewAdapter(getContext(),nameVictimHistory,scanTime,ageVictimHistory);
        listView.setAdapter(customHistoryListViewAdapter);
    }
}
