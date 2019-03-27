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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.R;
import stt.umc.feature.Request.PatientRequest;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private String[] clsRoom ={"201","203"};
    private String[] clsName={"Xét nghiệm","Siêu âm"};
    private Integer[] clsCurrentNumber={200,201};
    private Integer[] clsYourNumber={202,215};
    private String[] clsTime={"11:30","12:30"};

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
        Bundle bundle = this.getArguments();
        String sb= bundle.getString("patient");
        JSONObject json = null;
        try {
            json = new JSONObject(sb.substring(1,sb.length()-1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PatientRequest patientRequest = new PatientRequest(json);
        //set profile view
        getRoom("https://fit-umc-stt.azurewebsites.net/patient/thongtinkhambenh/"+patientRequest.getPatientID());
        CustomCLSGridViewAdapter customCLSGridViewAdapter=new CustomCLSGridViewAdapter(view.getContext(),clsRoom,clsName,clsCurrentNumber,clsYourNumber,clsTime);
        gridView.setAdapter(customCLSGridViewAdapter);
        // Inflate the layout for this fragment
        return view;
    }

    public static StringBuilder getRoom(final String urlString) {
        //PatientRunnable mPatientRunnable =  new PatientRunnable(urlString);
        //Thread mThread = new Thread(mPatientRunnable);
        //mThread.start();
        StringBuilder roomRequest = new StringBuilder();
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
            } else {
                Log.d("Error ", "");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return mPatientRunnable.getPatientRequest();
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
