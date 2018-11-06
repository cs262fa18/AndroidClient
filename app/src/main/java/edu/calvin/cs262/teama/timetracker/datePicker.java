package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class datePicker extends AppCompatActivity {

    DatePicker pickDate;
    Button saveDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickDate=(DatePicker) findViewById(R.id.datePickerThing);
        saveDateButton=(Button)findViewById(R.id.saveDateButton);
        saveDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String timePickedDay=Integer.toString(pickDate.getDayOfMonth());
                String timePickedMonth=Integer.toString(pickDate.getMonth());
                String timePickedYear=Integer.toString(pickDate.getYear());
                Intent intent=new Intent();
                intent.putExtra("timePickedDay",timePickedDay);
                intent.putExtra("timePickedMonth",timePickedMonth);
                intent.putExtra("timePickedYear",timePickedYear);
                setResult(1,intent);
                finish();//finishing activity
            }
        });
    }

}
