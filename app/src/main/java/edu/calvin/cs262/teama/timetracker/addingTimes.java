package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

import java.util.ArrayList;

public class addingTimes extends AppCompatActivity {


    Button startTimeButton;
    Button endTimeButton;
    Button dateButton;
    Button saveButton;
    Spinner projSpinner;
    ArrayList<String> ActivitiesList = new ArrayList<String>();
    TextView StartTimeTextView;
    TextView EndTimeTextView;
    TextView DateTextView;
    EditText userNameText;
    String finalUsername;
    String finalProject;
    String finalStartTimeHour;
    String finalStartTimeMin;
    String finalEndTimeHour;
    String finalEndTimeMin;
    String finalStartDay;
    String finalStartMonth;
    String finalStartYear;
    String finalEndDay;
    String finalEndMonth;
    String finalEndYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int sizeOfList = Integer.parseInt(bundle.get("activitiesListSize").toString());
        if (sizeOfList != 0) {
            for (int b = 0; b < sizeOfList; b++) {
                ActivitiesList.add(bundle.get("activitiesList" + b).toString());
            }
        }

        setContentView(R.layout.activity_adding_times);

        StartTimeTextView=(TextView)findViewById(R.id.startTimeText);
        EndTimeTextView=(TextView)findViewById(R.id.endTimeText);
        DateTextView=(TextView)findViewById(R.id.dateText);
        userNameText=(EditText)findViewById(R.id.UserNameEdit);

        Date dateToday = new Date();
        finalStartDay = finalEndDay = Integer.toString(dateToday.getDay());
        finalStartMonth = finalEndMonth = Integer.toString(dateToday.getMonth());
        finalStartYear = finalEndYear = Integer.toString(dateToday.getYear());

        projSpinner = (Spinner)findViewById(R.id.projTimesSpin);
        ArrayAdapter<String> remAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, ActivitiesList);
        remAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projSpinner.setAdapter(remAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startTimeButton=(Button)findViewById(R.id.startTimeButton);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intentStartTime = new Intent();
                intentStartTime.setClass(getApplicationContext(), timePicker.class);
                startActivityForResult(intentStartTime, 1);
            }
        });

        endTimeButton=(Button)findViewById(R.id.endTimeButton);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intentEndTime = new Intent();
                intentEndTime.setClass(getApplicationContext(), timePicker.class);
                startActivityForResult(intentEndTime, 2);
            }
        });

        dateButton=(Button)findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intentDate = new Intent();
                intentDate.setClass(getApplicationContext(), datePicker.class);
                startActivityForResult(intentDate, 3);
            }
        });

        saveButton=(Button)findViewById(R.id.saveAllButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (userNameText.getText().toString().isEmpty() | StartTimeTextView.getText().toString().isEmpty() | EndTimeTextView.getText().toString().isEmpty() | DateTextView.getText().toString().isEmpty()) {
                    displayToast(getString(R.string.fill_all_feilds_error));
                } else {

                    finalUsername = userNameText.getText().toString();
                    finalProject = projSpinner.getSelectedItem().toString();
                    Intent intent = new Intent();
                    intent.putExtra("finalUsername", finalUsername);
                    intent.putExtra("finalProject", finalProject);
                    intent.putExtra("finalStartTimeHour", finalStartTimeHour);
                    intent.putExtra("finalStartTimeMin", finalStartTimeMin);
                    intent.putExtra("finalEndTimeHour", finalEndTimeHour);
                    intent.putExtra("finalEndTimeMin", finalEndTimeMin);
                    intent.putExtra("finalStartDay", finalStartDay);
                    intent.putExtra("finalStartMonth", finalStartMonth);
                    intent.putExtra("finalStartYear", finalStartYear);
                    intent.putExtra("finalEndDay", finalEndDay);
                    intent.putExtra("finalEndMonth", finalEndMonth);
                    intent.putExtra("finalEndYear", finalEndYear);
                    setResult(5, intent);
                    finish();//finishing activity
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1) {
            String newStartTimeHour = data.getExtras().get("timePickedHour").toString();
            String newStartTimeMin = data.getExtras().get("timePickedMin").toString();
            finalStartTimeHour = newStartTimeHour;
            finalStartTimeMin = newStartTimeMin;
            StartTimeTextView.setText(newStartTimeHour + ":" + newStartTimeMin);


        } else if(requestCode==2) {
            String newEndTimeHour = data.getExtras().get("timePickedHour").toString();
            String newEndTimeMin = data.getExtras().get("timePickedMin").toString();
            finalEndTimeHour = newEndTimeHour;
            finalEndTimeMin = newEndTimeMin;
            EndTimeTextView.setText(newEndTimeHour + ":" + newEndTimeMin);

        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
