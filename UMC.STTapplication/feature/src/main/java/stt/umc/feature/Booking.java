package stt.umc.feature;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.text.DateFormat;

import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.Toast;

import stt.umc.feature.fragments.DatePickerFragmen;

public class Booking extends AppCompatActivity implements  View.OnClickListener {
    private Button buttonBooking;
    private TextView in_date, in_time;
    private LinearLayout layoutDateTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Spinner spin;
    String arr[] = {
            "Khoa nội",
            "Khoa ngoại"};
    //Gán Data source (arr) vào Adapter
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        buttonBooking = findViewById(R.id.btnBooking);
        //  bookingCalendarView = findViewById(R.id.CalendarLayout);
        spin=(Spinner) findViewById(R.id.static_spinner);
       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        //set adapter vào sp (spinner)
        //spin.setAdapter(adapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (
                       this,
                        R.layout.m_spinner_item,
                        arr
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        in_date = findViewById(R.id.in_date);
        in_time = findViewById(R.id.in_time);
        in_date.setOnClickListener(this);
        in_time.setOnClickListener(this);
        //  layoutDateTime = findViewById(R.id.layoutDateTime);
        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bookingCalendarView.setVisibility(View.VISIBLE);
                DialogFragment datePicker = new DatePickerFragmen();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == in_date) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            in_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == in_time) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            in_time.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

}
