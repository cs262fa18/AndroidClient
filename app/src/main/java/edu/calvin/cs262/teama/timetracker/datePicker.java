package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

/**
 * This class handles the Date Picker for the adding times class
 *
 * @author Quentin Barnes
 */

public class datePicker extends AppCompatActivity {

    DatePicker pickDate;
    Button saveDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        setTitle("Pick a Date");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickDate = (DatePicker) findViewById(R.id.datePickerThing);
        saveDateButton = (Button) findViewById(R.id.saveDateButton);
        saveDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String timePickedDay = Integer.toString(pickDate.getDayOfMonth());
                String timePickedMonth = Integer.toString(pickDate.getMonth());
                String timePickedYear = Integer.toString(pickDate.getYear());
                try {
                    String timePickedHour = getIntent().getExtras().get("timePickedHour").toString();
                    String timePickedMin = getIntent().getExtras().get("timePickedMin").toString();

                    Intent intent = new Intent();
                    intent.putExtra("timePickedDay", timePickedDay);
                    intent.putExtra("timePickedMonth", timePickedMonth);
                    intent.putExtra("timePickedYear", timePickedYear);
                    intent.putExtra("timePickedHour", timePickedHour);
                    intent.putExtra("timePickedMin", timePickedMin);
                    setResult(1, intent);
                    finish();//finishing activity
                } catch (NullPointerException e) {

                }
            }
        });
    }

}
