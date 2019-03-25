package stt.umc.feature.fragments;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.HashMap;

import stt.umc.feature.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        //Timeout spinner set up
        String arrTimeOut[] = {
                "10 phút",
                "30 phút",
                "60 phút"};
        ArrayAdapter<String> adapterTimeOut = new ArrayAdapter<String>
                (
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        arrTimeOut
                );
        adapterTimeOut.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        Spinner timeOutSpinner = view.findViewById(R.id.timeOutSpinner);
        timeOutSpinner.setAdapter(adapterTimeOut);
        //Repeat spinner set up
        String repeatArr[] = {
                "1 lần",
                "2 lần",
                "3 lần"};
        ArrayAdapter<String> adapterRepeat = new ArrayAdapter<String>
                (
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        repeatArr
                );
        adapterRepeat.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        Spinner repeatSpinner = view.findViewById(R.id.repeatSpinner);
        repeatSpinner.setAdapter(adapterRepeat);
        //Ring Tone spinner set up
        ArrayAdapter<String> adapterRingTone = new ArrayAdapter<String>
                (
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        (String[])listRingtones().keySet().toArray()
                );
        Spinner ringToneSpinner = view.findViewById(R.id.ringToneSpinner);
        repeatSpinner.setAdapter(adapterRingTone);

        //Radio button set up
        RadioButton rdtbnNoti = view.findViewById(R.id.rdbtnNotification);
        if(rdtbnNoti.isChecked()){
            NotificationCompat.Builder mBuider= new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.drawable.umc_logo)
                    .setContentTitle("Nhắc nhở khám bệnh")
                    .setContentText("Còn " + timeOutSpinner.getSelectedItem().toString()+" tới giờ khám");;
        }
        RadioButton rdtbnVibrate = view.findViewById(R.id.rdbtnVibrate);
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
        return view;
    }

    public HashMap<String,String> listRingtones() {
        HashMap<String,String> ringTonesHashmap = new HashMap<>();
        RingtoneManager manager = new RingtoneManager(getContext());
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            Uri ringtoneURI = manager.getRingtoneUri(cursor.getPosition());
            ringTonesHashmap.put(title,ringtoneURI.toString());
        }
        return ringTonesHashmap;
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
