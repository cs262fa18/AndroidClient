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
    Button saveButton;
    Spinner projSpinner;
    ArrayList<String> ActivitiesList = new ArrayList<String>();
    TextView StartTimeTextView;
    TextView EndTimeTextView;
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
    Boolean startAutoSet;
    Boolean endAutoSet;

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

        saveButton=(Button)findViewById(R.id.saveAllButton);
        StartTimeTextView=(TextView)findViewById(R.id.startTimeText);
        EndTimeTextView=(TextView)findViewById(R.id.endTimeText);
        userNameText=(EditText)findViewById(R.id.UserNameEdit);
        startAutoSet=true;
        endAutoSet=true;

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


        saveButton=(Button)findViewById(R.id.saveAllButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (userNameText.getText().toString().isEmpty() | StartTimeTextView.getText().toString().isEmpty() | EndTimeTextView.getText().toString().isEmpty()) {
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

        if (requestCode==1) {
            if (endAutoSet) {
                finalStartTimeHour = data.getExtras().get("timePickedHour").toString();
                finalStartTimeMin = data.getExtras().get("timePickedMin").toString();
                finalStartDay = data.getExtras().get("timePickedDay").toString();
                finalStartMonth = data.getExtras().get("timePickedMonth").toString();
                finalStartYear = data.getExtras().get("timePickedYear").toString();

                StartTimeTextView.setText(finalStartTimeHour + ":" + finalStartTimeMin + "\n" + finalStartMonth + "/" + finalStartDay + "/" + finalStartYear);

                if (EndTimeTextView.getText().toString().isEmpty() | endAutoSet) {
                    finalEndTimeHour = Integer.toString(Integer.parseInt(finalStartTimeHour) + 2);
                    finalEndTimeMin = finalStartTimeMin;
                    finalEndDay = finalStartDay;
                    finalEndMonth = finalStartMonth;
                    finalEndYear = finalStartYear;
                    if (Integer.parseInt(finalEndTimeHour) >= 24) {
                        finalEndTimeHour = Integer.toString(Integer.parseInt(finalEndTimeHour) - 24);
                        finalEndDay = Integer.toString(Integer.parseInt(finalEndDay) + 1);
                    }

                    EndTimeTextView.setText(finalEndTimeHour + ":" + finalEndTimeMin + "\n" + finalEndMonth + "/" + finalEndDay + "/" + finalEndYear);
                }
                startAutoSet = false;

            } else  {
                Date endDate = new Date(Integer.parseInt(finalEndYear), Integer.parseInt(finalEndMonth), Integer.parseInt(finalEndDay), Integer.parseInt(finalEndTimeHour), Integer.parseInt(finalEndTimeMin));
                Date startDate = new Date(Integer.parseInt(data.getExtras().get("timePickedYear").toString()), Integer.parseInt(data.getExtras().get("timePickedMonth").toString()), Integer.parseInt(data.getExtras().get("timePickedDay").toString()), Integer.parseInt(data.getExtras().get("timePickedHour").toString()), Integer.parseInt(data.getExtras().get("timePickedMin").toString()));

                if (startDate.before(endDate)) {
                    finalStartTimeHour = data.getExtras().get("timePickedHour").toString();
                    finalStartTimeMin = data.getExtras().get("timePickedMin").toString();
                    finalStartDay = data.getExtras().get("timePickedDay").toString();
                    finalStartMonth = data.getExtras().get("timePickedMonth").toString();
                    finalStartYear = data.getExtras().get("timePickedYear").toString();

                    StartTimeTextView.setText(finalStartTimeHour + ":" + finalStartTimeMin + "\n" + finalStartMonth + "/" + finalStartDay + "/" + finalStartYear);

                    if (EndTimeTextView.getText().toString().isEmpty() | endAutoSet) {
                        finalEndTimeHour = Integer.toString(Integer.parseInt(finalStartTimeHour) + 2);
                        finalEndTimeMin = finalStartTimeMin;
                        finalEndDay = finalStartDay;
                        finalEndMonth = finalStartMonth;
                        finalEndYear = finalStartYear;
                        if (Integer.parseInt(finalEndTimeHour) >= 24) {
                            finalEndTimeHour = Integer.toString(Integer.parseInt(finalEndTimeHour) - 24);
                            finalEndDay = Integer.toString(Integer.parseInt(finalEndDay) + 1);
                        }

                        EndTimeTextView.setText(finalEndTimeHour + ":" + finalEndTimeMin + "\n" + finalEndMonth + "/" + finalEndDay + "/" + finalEndYear);
                    }
                    startAutoSet = false;
                } else {
                    displayToast("Start Date Must Be Before End Date");
                }
            }

        } else if (requestCode==2) {
            if (startAutoSet) {
                finalEndTimeHour = data.getExtras().get("timePickedHour").toString();
                finalEndTimeMin = data.getExtras().get("timePickedMin").toString();
                finalEndDay = data.getExtras().get("timePickedDay").toString();
                finalEndMonth = data.getExtras().get("timePickedMonth").toString();
                finalEndYear = data.getExtras().get("timePickedYear").toString();

                EndTimeTextView.setText(finalEndTimeHour + ":" + finalEndTimeMin + "\n" + finalEndMonth + "/" + finalEndDay + "/" + finalEndYear);

                if (StartTimeTextView.getText().toString().isEmpty()) {
                    finalStartTimeHour = Integer.toString(Integer.parseInt(finalEndTimeHour) - 2);
                    finalStartTimeMin = finalEndTimeMin;
                    finalStartDay = finalEndDay;
                    finalStartMonth = finalEndMonth;
                    finalStartYear = finalEndYear;
                    if (Integer.parseInt(finalStartTimeHour) <= -1) {
                        finalStartTimeHour = Integer.toString(Integer.parseInt(finalStartTimeHour) + 24);
                        finalStartDay = Integer.toString(Integer.parseInt(finalStartDay) - 1);
                    }

                    StartTimeTextView.setText(finalStartTimeHour + ":" + finalStartTimeMin + "\n" + finalStartMonth + "/" + finalStartDay + "/" + finalStartYear);
                }
                endAutoSet = false;


            } else {

                Date startDate = new Date(Integer.parseInt(finalStartYear), Integer.parseInt(finalStartMonth), Integer.parseInt(finalStartDay), Integer.parseInt(finalStartTimeHour), Integer.parseInt(finalStartTimeMin));
                Date endDate = new Date(Integer.parseInt(data.getExtras().get("timePickedYear").toString()), Integer.parseInt(data.getExtras().get("timePickedMonth").toString()), Integer.parseInt(data.getExtras().get("timePickedDay").toString()), Integer.parseInt(data.getExtras().get("timePickedHour").toString()), Integer.parseInt(data.getExtras().get("timePickedMin").toString()));


                if (endDate.after(startDate)) {
                    finalEndTimeHour = data.getExtras().get("timePickedHour").toString();
                    finalEndTimeMin = data.getExtras().get("timePickedMin").toString();
                    finalEndDay = data.getExtras().get("timePickedDay").toString();
                    finalEndMonth = data.getExtras().get("timePickedMonth").toString();
                    finalEndYear = data.getExtras().get("timePickedYear").toString();

                    EndTimeTextView.setText(finalEndTimeHour + ":" + finalEndTimeMin + "\n" + finalEndMonth + "/" + finalEndDay + "/" + finalEndYear);

                    if (StartTimeTextView.getText().toString().isEmpty()) {
                        finalStartTimeHour = Integer.toString(Integer.parseInt(finalEndTimeHour) - 2);
                        finalStartTimeMin = finalEndTimeMin;
                        finalStartDay = finalEndDay;
                        finalStartMonth = finalEndMonth;
                        finalStartYear = finalEndYear;
                        if (Integer.parseInt(finalStartTimeHour) <= -1) {
                            finalStartTimeHour = Integer.toString(Integer.parseInt(finalStartTimeHour) + 24);
                            finalStartDay = Integer.toString(Integer.parseInt(finalStartDay) - 1);
                        }

                        StartTimeTextView.setText(finalStartTimeHour + ":" + finalStartTimeMin + "\n" + finalStartMonth + "/" + finalStartDay + "/" + finalStartYear);
                    }
                    endAutoSet = false;
                } else {
                        displayToast("End Date Must Be After Start Date");
                }
            }
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
