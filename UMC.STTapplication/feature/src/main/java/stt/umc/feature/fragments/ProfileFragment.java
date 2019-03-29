package stt.umc.feature.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import stt.umc.feature.DatLichKhamActivity;
import stt.umc.feature.R;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Utils.GlobalUtils;
import stt.umc.feature.interfaces.DialogCallback;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String BUTTON_STATE = "Button_State";
    public static final int ALREADY_LOGIN = 001;
    public static final int NOT_LOGIN = 000;

    RadioGroup radioGroupTime;
    RadioButton radioBtn5, radioBtn15, radioBtn30;
    SharedPreferences sharedpreferences;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle bundle= this.getArguments();
        String sb= bundle.getString("patient");
        JSONObject json = null;
        try {
            json = new JSONObject(sb.substring(1,sb.length()-1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PatientRequest patientRequest = new PatientRequest(json);
        //set profile view
        TextView tvFullName = view.findViewById(R.id.tvFullName);
        tvFullName.setText(patientRequest.getLastName()+" " + patientRequest.getMiddleName()+" "+patientRequest.getFirstName());
        TextView tvBirthday = view.findViewById(R.id.tvBirthday);
        String birthday[] = patientRequest.getPatientBirthday().substring(0,10).split("-");
        tvBirthday.setText(birthday[2]+"/"+birthday[1]+"/"+birthday[0]);
        TextView tvGender = view.findViewById(R.id.tvGender);
        tvGender.setText(patientRequest.getGender());
        Button button = view.findViewById(R.id.btnDatLich);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          startActivity(new Intent(getActivity(), DatLichKhamActivity.class));
                                      }
                                  });

        /*radioGroupTime = (RadioGroup)view.findViewById(R.id.radioGrTime);
        radioBtn5 = (RadioButton)view.findViewById(R.id.radioBtn5);
        radioBtn15 = (RadioButton)view.findViewById(R.id.radioBtn15);
        radioBtn30 = (RadioButton)view.findViewById(R.id.radioBtn30);*/
                // helper method to open up the file.
                sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        // grab the last saved state here on each activity start


        /*radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioBtn = group.getCheckedRadioButtonId();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                *//*if (checkedRadioBtn == R.id.radioBtn5){
                    Toast.makeText(view.getContext(), "Hiện thông báo trước khi khám 5 phút", Toast.LENGTH_LONG).show();
                } else if(checkedRadioBtn == R.id.radioBtn15){
                    Toast.makeText(view.getContext(), "Hiện thông báo trước khi khám 15 phút", Toast.LENGTH_LONG).show();
                } else if(checkedRadioBtn == R.id.radioBtn30){
                    Toast.makeText(view.getContext(), "Hiện thông báo trước khi khám 30 phút", Toast.LENGTH_LONG).show();
                }*//*
                editor.putInt(BUTTON_STATE,checkedRadioBtn);
                editor.apply();
            }
        });*/
        /*if(sharedpreferences.getInt(BUTTON_STATE,0) == R.id.radioBtn5){
            radioBtn5.setChecked(true);
        } else if(sharedpreferences.getInt(BUTTON_STATE,0) == R.id.radioBtn15) {
            radioBtn15.setChecked(true);
        } else if(sharedpreferences.getInt(BUTTON_STATE,0) == R.id.radioBtn30) {
            radioBtn30.setChecked(true);
        }*/

        //Button logout
        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LOGIN_STATE",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("LOGIN_STATE",NOT_LOGIN);
                /*GlobalUtils.showRatingDialog(v.getContext(), new DialogCallback() {
                    @Override
                    public void callback(int rating) {

                    }
                });*/
                //getActivity().finish();
            }
        });
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
}
