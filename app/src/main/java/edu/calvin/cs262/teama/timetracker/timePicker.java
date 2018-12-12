package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * This class is used by the TimePicker class in android.
 * You select the hour and minute from the interface and it passes
 * it on to the datepicker class which passes it back here.  Then this
 * class passes it all back to the addingTimes class.
 */
public class timePicker extends AppCompatActivity {

    TimePicker pickTime;
    Button saveTimeButton;

    /**
     * Once times are picked from the interface, allows user to
     * pass them on to the datePicker class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        setTitle("Pick a Time");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickTime = (TimePicker) findViewById(R.id.timePickerThing);
        saveTimeButton = (Button) findViewById(R.id.saveTimeButton);
        saveTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String timePickedHour = Integer.toString(pickTime.getHour());
                String timePickedMin = Integer.toString(pickTime.getMinute());
                Intent intent = new Intent();
                intent.putExtra("timePickedHour", timePickedHour);
                intent.putExtra("timePickedMin", timePickedMin);
                intent.setClass(getApplicationContext(), datePicker.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Takes the data from DatePicker class and returns it to addingTimes class
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            try {
                Intent intent = new Intent();
                intent.putExtra("timePickedDay", data.getExtras().get("timePickedDay").toString());
                intent.putExtra("timePickedMonth", data.getExtras().get("timePickedMonth").toString());
                intent.putExtra("timePickedYear", data.getExtras().get("timePickedYear").toString());
                intent.putExtra("timePickedHour", data.getExtras().get("timePickedHour").toString());
                intent.putExtra("timePickedMin", data.getExtras().get("timePickedMin").toString());

                setResult(1, intent);
                finish();//finishing activity

            } catch (NullPointerException e) {

            }
        }
    }

}
