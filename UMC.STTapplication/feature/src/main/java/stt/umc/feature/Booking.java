package stt.umc.feature;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;
import java.text.DateFormat;

import stt.umc.feature.fragments.DatePickerFragmen;

public class Booking extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button buttonBooking;
    private View bookingCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        buttonBooking = findViewById(R.id.btnBooking);
        bookingCalendarView = findViewById(R.id.CalendarLayout);
        buttonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bookingCalendarView.setVisibility(View.VISIBLE);
                DialogFragment datePicker = new DatePickerFragmen();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    }
}
