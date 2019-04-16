package stt.umc.feature.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.R;
import stt.umc.feature.Request.ClinicalMedicalTicketRequest;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Request.TicketInformationRequest;
import stt.umc.feature.receiver.MyAlarmReceiver;

import static stt.umc.feature.Utils.GlobalUtils.getTicket;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public static HomeFragment homeFragment;
    public static NotificationManagerCompat notificationManager;

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
        notificationManager = NotificationManagerCompat.from(getContext());
        homeFragment = this;
    }

    private View mGridView;
    private View mTextNotice;
    private View mHomeContent;
    private View mLoadingProgress;

    private LinearLayout linearLayouLS;
    private TextView tvPhongKham;
    private TextView tvCurrentNumber;
    private TextView tvTime;
    private TextView tvYourNumber;
    private TextView tvWaitTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mGridView = view.findViewById(R.id.homeGrid);
        mTextNotice = view.findViewById(R.id.txt_notice);
        mHomeContent = view.findViewById(R.id.homeContent);
        mLoadingProgress = view.findViewById(R.id.loadingPanel);
        tvPhongKham = view.findViewById(R.id.idPhongKham);
        tvCurrentNumber = view.findViewById(R.id.currentNumber);
        tvTime = view.findViewById(R.id.idThoiGianDuKien);
        tvYourNumber = view.findViewById(R.id.yourNumber);
        tvWaitTime = view.findViewById(R.id.waitTime);
        linearLayouLS = view.findViewById(R.id.homeContent);
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

    boolean haveReceiveResult = false;


    public void onFailed() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                haveReceiveResult = true;
                mGridView.setVisibility(View.INVISIBLE);
                mTextNotice.setVisibility(View.VISIBLE);
                mHomeContent.setVisibility(View.INVISIBLE);
                mLoadingProgress.setVisibility(View.INVISIBLE);

                ((TextView) mTextNotice).setText("ERROR UNABLE TO FOUND THIS PATIENT");
            }
        });
    }

    public void onLoadingDataComplete(final boolean completed) {
        if (!completed) {
            mGridView.setVisibility(View.INVISIBLE);
            mTextNotice.setVisibility(View.INVISIBLE);
            mHomeContent.setVisibility(View.INVISIBLE);
            mLoadingProgress.setVisibility(View.VISIBLE);
        } else {
            mGridView.setVisibility(View.VISIBLE);
            mTextNotice.setVisibility(View.VISIBLE);
            mHomeContent.setVisibility(View.VISIBLE);
            mLoadingProgress.setVisibility(View.INVISIBLE);
        }

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(!completed) {
//                    mGridView.setVisibility(View.INVISIBLE);
//                    mTextNotice.setVisibility(View.INVISIBLE);
//                    mHomeContent.setVisibility(View.INVISIBLE);
//                    mLoadingProgress.setVisibility(View.VISIBLE);
//                }
//                else {
//                    mGridView.setVisibility(View.VISIBLE);
//                    mTextNotice.setVisibility(View.VISIBLE);
//                    mHomeContent.setVisibility(View.VISIBLE);
//                    mLoadingProgress.setVisibility(View.INVISIBLE);
//                }
//            }
//        } , 500);
    }

    public void onReceivingData(String data) {
        String sb = data;
        JSONObject json = null;
        try {
            json = new JSONObject(sb.substring(1, sb.length() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PatientRequest patientRequest = new PatientRequest(json);
        //set profile view
        String url = "https://fit-umc-stt.azurewebsites.net/clinic/thongtinkhambenh/" + patientRequest.getPatientID();
        //String testUrl = "https://fit-umc-stt.azurewebsites.net/patient/thongtinkhambenh/BN21677";
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
            clsTime[i] = informationRequest.getCangLamSang().get(i).getExpectedTime();
            clsName[i] = informationRequest.getCangLamSang().get(i).getFunctionalName();
        }

        String[] mSplitString = cmTicketRequest.getExpectedTime().split(":");

        SharedPreferences sharedPreferences;
        //Call set up notification
        sharedPreferences = getContext().getSharedPreferences("SETTING", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String time_out = sharedPreferences.getString("time_out", "0");
        Calendar calendarCurrent = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mSplitString[0]));
        //calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, Integer.parseInt(mSplitString[1]));
        //calendar.set(Calendar.MINUTE, 43);
        tvPhongKham.setText(cmTicketRequest.getRoomID());
        tvCurrentNumber.setText(String.format(cmTicketRequest.getRoomCurrentNumber().toString()));
        tvYourNumber.setText(String.format(cmTicketRequest.getRoomCurrentNumber().toString()));
        tvTime.setText(cmTicketRequest.getExpectedTime());
        long longMilli = 0;
        long longMinute = 0;
        long longHour = 0;
        String waitTimeText;
        if (calendarCurrent.compareTo(calendar) <= 0) {
            longMilli =  calendar.getTimeInMillis()-calendarCurrent.getTimeInMillis();
            longMinute = (longMilli/1000)/60;
            longHour = longMinute/60;
            longMinute = longMinute%60;
            startTimer(longMilli,1000*60);
            waitTimeText= String.valueOf(longHour)+" giờ "+String.valueOf(longMinute) +" phút tới giờ khám";

        } else {
            waitTimeText = "Đã quá giờ khám";
            /*int color = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                color = Color.argb(0.5f,0f,0f,0f);
            }
            linearLayouLS.setVisibility(View.VISIBLE);
            linearLayouLS.setBackgroundColor(color);*/
        }
        tvWaitTime.setText(waitTimeText);

        Intent notifyIntent = new Intent(getContext(), MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getContext(), 100, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - Integer.parseInt(time_out.split(" ")[0]) * 60 * 1000
                , pendingIntent);


        CustomCLSGridViewAdapter customCLSGridViewAdapter = new CustomCLSGridViewAdapter(this.getContext(),
                clsRoom,
                clsName,
                clsCurrentNumber,
                clsYourNumber,
                clsTime);
        ((GridView) mGridView).setAdapter(customCLSGridViewAdapter);
        onLoadingDataComplete(true);
    }


    //Declare timer
    public static CountDownTimer cTimer = null;

    //start timer function
    void startTimer(final long milli, long interval) {
        cTimer = new CountDownTimer(milli, interval) {
            long longMinute = (milli/1000)/60;
            long longHour = longMinute/60;
            long finallongMinute = longMinute%60;
            public void onTick(long millisUntilFinished) {
                String waitTimeText= String.valueOf(longHour)+" giờ "+String.valueOf(finallongMinute) +" phút tới giờ khám";
                tvWaitTime.setText(waitTimeText);
                finallongMinute -= 1;
                if(finallongMinute <0) {
                    cTimer.cancel();
                }
            }
            public void onFinish() {
            }
        };
        cTimer.start();
    }



    //cancel timer
    public void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
}
