package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class timePicker extends AppCompatActivity {

    TimePicker pickTime;
    Button saveTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickTime=(TimePicker)findViewById(R.id.timePickerThing);
        saveTimeButton=(Button)findViewById(R.id.saveTimeButton);
        saveTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String timePickedHour=Integer.toString(pickTime.getHour());
                String timePickedMin=Integer.toString(pickTime.getMinute());
                Intent intent=new Intent();
                intent.putExtra("timePickedHour",timePickedHour);
                intent.putExtra("timePickedMin",timePickedMin);
                setResult(1,intent);
                finish();//finishing activity
            }
        });

    }

}
