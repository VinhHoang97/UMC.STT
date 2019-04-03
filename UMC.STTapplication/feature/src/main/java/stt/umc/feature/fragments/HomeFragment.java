package stt.umc.feature.fragments;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import stt.umc.feature.CustomView.CustomCLSGridViewAdapter;
import stt.umc.feature.Home;
import stt.umc.feature.R;
import stt.umc.feature.Request.ClinicalMedicalTicketRequest;
import stt.umc.feature.Request.PatientRequest;
import stt.umc.feature.Request.TicketInformationRequest;

import static stt.umc.feature.MyNotificationChannel.CHANNEL_ON_TIME;
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

    public NotificationManagerCompat notificationManager;
    SharedPreferences sharedPreferences;
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
    }

    private View mGridView;
    private View mTextNotice;
    private View mHomeContent;
    private View mLoadingProgress;


    private TextView tvPhongKham;
    private TextView tvCurrentNumber;
    private TextView tvTime;
    private TextView tvYourNumber;

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
        tvPhongKham.setText(cmTicketRequest.getRoomID());
        tvCurrentNumber.setText(String.format(cmTicketRequest.getRoomCurrentNumber().toString()));
        tvYourNumber.setText(String.format(cmTicketRequest.getRoomCurrentNumber().toString()));
        tvTime.setText(cmTicketRequest.getExpectedTime());
        CustomCLSGridViewAdapter customCLSGridViewAdapter = new CustomCLSGridViewAdapter(this.getContext(),
                clsRoom,
                clsName,
                clsCurrentNumber,
                clsYourNumber,
                clsTime);
        ((GridView) mGridView).setAdapter(customCLSGridViewAdapter);

        //Call set up notification
        sharedPreferences = this.getActivity().getSharedPreferences("SETTING", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String time_out = sharedPreferences.getString("time_out","");
        String repeat = sharedPreferences.getString("repeat","");
        String ring_tone = sharedPreferences.getString("ring_tone_uri","");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,2);
        long occur = calendar.getTimeInMillis();
        onSetUpNotification(Uri.parse(ring_tone),repeat,time_out,true,true,occur);
        onLoadingDataComplete(true);
    }


    //Set up notification when getting data
    public void onSetUpNotification(Uri ringTone,
                                    String repeat,
                                    String time_out,
                                    boolean vibrate,
                                    boolean notifi,
                                    long occur) {
        //creating and assigning value to alarm manager class
        if (vibrate) {
            Intent activityIntent = new Intent(getContext(), Home.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, activityIntent, 0);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(), CHANNEL_ON_TIME);
            notification.setSmallIcon(R.drawable.umc_logo)
                    .setContentIntent(contentIntent)
                    .setContentTitle("On Time")
                    .setContentText("Còn " + time_out+ " đến giờ khám")
                    .setVibrate(new long[]{1000, 1000, 1000,1000 ,1000})
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setAutoCancel(true)
                    .setSound(ringTone)
                    .setWhen(occur)
                    .setShowWhen(true);
            notificationManager.notify(1, notification.build());
        }

       /* Calendar Alarm = Calendar.getInstance();
        Alarm.set(Calendar.HOUR_OF_DAY,17);
        Alarm.set(Calendar.MINUTE, 35);
        Intent AlarmIntent = new Intent(thi, AlarmReceiver.class);
        AlarmManager AlmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent Sender = PendingIntent.getBroadcast(Home.this, 0, AlarmIntent, 0);
        AlmMgr.set(AlarmManager.RTC_WAKEUP, Alarm.getTimeInMillis(), Sender);*/
    }
}
