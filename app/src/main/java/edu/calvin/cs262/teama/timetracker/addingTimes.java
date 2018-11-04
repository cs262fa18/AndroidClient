package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class addingTimes extends AppCompatActivity {


    Button startTimeButton;
    Button endTimeButton;
    Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_times);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startTimeButton=(Button)findViewById(R.id.startTimeButton);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intentStartTime = new Intent();
                intentStartTime.setClass(getApplicationContext(), timePicker.class);
                startActivity(intentStartTime);
            }
        });

        endTimeButton=(Button)findViewById(R.id.endTimeButton);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intentEndTime = new Intent();
                intentEndTime.setClass(getApplicationContext(), timePicker.class);
                startActivity(intentEndTime);
            }
        });

        dateButton=(Button)findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intentDate = new Intent();
                intentDate.setClass(getApplicationContext(), datePicker.class);
                startActivity(intentDate);
            }
        });
    }

}
