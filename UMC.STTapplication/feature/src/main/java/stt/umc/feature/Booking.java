package stt.umc.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Booking extends AppCompatActivity {

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
                bookingCalendarView.setVisibility(View.VISIBLE);
            }
        });
    }
}
