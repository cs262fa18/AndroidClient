package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private Spinner spinActivities;
    private ArrayList<String> activitiesList = new ArrayList<String>();

    private ImageView playPause;
    private TextView timerText;
    private TextView todaysTimeText;
    private TimeEntry current_time_entry;
    private boolean is_starting_up;
    public static CSVImportExport csv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create temp items for array
        activitiesList = Project.getActivitiesList();

        setContentView(R.layout.activity_main);
        timerText = (TextView)findViewById(R.id.timerText);
        todaysTimeText = (TextView)findViewById(R.id.todaysTimeText);
        playPause = (ImageView)findViewById(R.id.play);
        is_starting_up = true;

        // Create data storage csv object
        try {
            csv = new CSVImportExport(getApplicationContext());
            Log.i("CSV File exists", new Boolean(csv.getCSVFile().exists()).toString());
            Log.i("CSV File path", csv.getCSVFile().getAbsolutePath());
            Log.i("External files dir", getApplicationContext().getExternalFilesDir(null).getAbsolutePath());

            if(csv.getCSVFile().exists()) {
                // Import data from csv
                FileInputStream fis = new FileInputStream(csv.getCSVFile());
                String[][] imported_data = csv.importCSV(fis);
                fis.close();

                // Clear current list of time entries
                current_time_entry = null;
                TimeEntry.clearTimeEntries();

                for (int i = 1; i < imported_data.length; i++) {
                    // Start at 1, because we don't want to use the header row as data
                    UUID uuid;
                    String project;
                    String username;
                    Date time_start;
                    Date time_end;
                    int action;
                    boolean synced;

                    uuid = UUID.fromString(imported_data[i][0]);
                    project = imported_data[i][1];
                    username = imported_data[i][2];
                    time_start = new Date(imported_data[i][3]);
                    if (imported_data[i][4].equals("")) {
                        time_end = null;
                    } else {
                        time_end = new Date(imported_data[i][4]);
                    }
                    synced = Boolean.parseBoolean(imported_data[i][5]);
                    TimeEntry te = new TimeEntry(uuid, project, username, time_start, time_end, synced);

                    // Uncomment the following to log reading of TimeEntries from the file
//                    Log.d("Start TE", "Index " + i);
//                    Log.d("UUID", uuid.toString());
//                    Log.d("Project", project);
//                    Log.d("Username", username);
//                    Log.d("Time start", time_start.toString());
//                    if (time_end == null) {
//                        Log.d("Time end", "null");
//
//                    } else {
//                        Log.d("Time end", time_end.toString());
//                    }
//                    Log.d("Synced", Boolean.toString(synced));
//
//                    Log.d("Cond1",Boolean.toString(current_time_entry == null));
//                    Log.d("Cond1",Boolean.toString(current_time_entry == null));


                    if (current_time_entry == null && te.getEndTime() == null) {
                        Log.d("CurrTime","Setting index " + i);
                        current_time_entry = te;
                        playPause.setImageResource(R.drawable.start);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        startSpinner();
        runTimer();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_project) {
            Intent intent = new Intent(this, addProject.class);
            if (activitiesList.isEmpty()) {
                intent.putExtra("activitiesListSize", 0);
                startActivityForResult(intent, 2);
            } else {
                int b;
                for (b = 0; b < activitiesList.size(); b++) {
                    intent.putExtra("activitiesList" + b, activitiesList.get(b).toString());
                }
                intent.putExtra("activitiesListSize", activitiesList.size());
                startActivityForResult(intent, 2);
            }

        } else if (id == R.id.manual_time_entry) {
            Intent intent = new Intent(this, addingTimes.class);
            if (activitiesList.isEmpty()) {
                intent.putExtra("activitiesListSize", 0);
            } else {
                int b;
                for (b = 0; b < activitiesList.size(); b++) {
                    intent.putExtra("activitiesList" + b, activitiesList.get(b).toString());
                }
                intent.putExtra("activitiesListSize", activitiesList.size());
            }

            startActivityForResult(intent, 3);

        } else if (id == R.id.manual_time_removal) {

        } else if (id == R.id.view_times) {
            ArrayList<String> projectNames = new ArrayList<String>();
            ArrayList<Float> projectTime = new ArrayList<Float>();
            Random random = new Random();

            for (int t = 0; t < activitiesList.size(); t++) {
                projectNames.add(activitiesList.get(t));
                projectTime.add(Float.parseFloat(TimeEntry.getProjectTime(activitiesList.get(t))));
                Log.d("dataView", projectNames.get(t) + " : " + projectTime.get(t));
            }



            if (projectTime.size() == projectNames.size()) {
                Intent intent = new Intent(this, viewTimes.class);
                for (int i = 0; i < projectTime.size(); i++) {
                    intent.putExtra("projectName" + i, projectNames.get(i));
                    intent.putExtra("projectTime" + i, projectTime.get(i));
                }
                intent.putExtra("size", projectTime.size());

                startActivityForResult(intent, 4);
            } else { displayToast("Error"); }

        } else if (id == R.id.dark_theme_switch) {

        } // else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startSpinner() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "COMING SOON!", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinActivities = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, activitiesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinActivities.setAdapter(adapter);
        spinActivities.setOnItemSelectedListener(this);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void showStartMsg(View view){
        toggleTimerRunning(null);
        showMsg("Start time!");
    }

    public void showMsg(String message) {
        displayToast(message);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startTimer() {
        playPause.setImageResource(R.drawable.start);
        current_time_entry = new TimeEntry((String) spinActivities.getSelectedItem(), "Prof. Vander Linden", new Date(), null, false);
        updateTimes();
        Log.d("CS262", "Starting timer");
    }

    private void stopTimer() {
        playPause.setImageResource(R.drawable.play);
        current_time_entry.setEndTime(new Date());
        current_time_entry = null;
        updateTimes();
        Log.d("CS262", "Stopping timer");
    }

    public void toggleTimerRunning(View view){
        if (timerIsRunning()) {
            // Stop timer
            stopTimer();
        } else {
            // Start timer
            startTimer();
        }
        Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
        saveAndSyncThread.start();
    }

    private String formatTime(long millis) {
        int seconds_passed_total = (int) (millis / 1000);
        int seconds_passed_partial = seconds_passed_total % 60;
        int minutes_passed_total = seconds_passed_total / 60;
        int minutes_passed_partial = minutes_passed_total % 60;
        int hours_passed = minutes_passed_total / 60;
        return String.format("%d:%02d:%02d", hours_passed, minutes_passed_partial, seconds_passed_partial);
    }

    private String getElapsedTime() {
        long millis = (new Date()).getTime() - current_time_entry.getStartTime().getTime();
        return formatTime(millis);
    }

    private String getTodaysElapsedtime() {
        long millis = 0;
        if (current_time_entry != null) {
            millis = (new Date()).getTime() - current_time_entry.getStartTime().getTime();
        }
        for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
            Date start_of_today = getStartOfDay();
            if (start_of_today.compareTo(te.getStartTime()) < 0 && te.getEndTime() != null) {
                // A previous and completed time entry from today.
                millis += (te.getEndTime().getTime() - te.getStartTime().getTime());
            }
        }
        return formatTime(millis);
    }

    private Date getStartOfDay() {
        Calendar start_of_day = Calendar.getInstance();
        start_of_day.set(
                start_of_day.get(Calendar.YEAR),
                start_of_day.get(Calendar.MONTH),
                start_of_day.get(Calendar.DATE),
                0, // Hour
                0, // Minute
                0 // Second
        );
        return new Date(start_of_day.getTimeInMillis());
    }

    public void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateTimes();
                handler.post(this);
            }
        });
    }

    public void updateTimes() {
        if(timerIsRunning())
            timerText.setText(getElapsedTime());
        todaysTimeText.setText("Today: " + getTodaysElapsedtime());
    }

    private boolean timerIsRunning() {
        return (this.current_time_entry != null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if((requestCode==2) & (resultCode==2))
        {
            String newProjName = data.getExtras().get("addedProj").toString();
            if (newProjName.isEmpty()) {
                displayToast(getString(R.string.addEmptyProjectError));
            } else {
                displayToast("New Project Added: " + newProjName);
                Project.addProject(newProjName);
                activitiesList = Project.getActivitiesList();
            }
        } else if((requestCode==2) & (resultCode==3)) {
            String removeProjName = data.getExtras().get("removeProj").toString();
            if (removeProjName.isEmpty()) {
                displayToast(getString(R.string.addEmptyProjectError));
            } else {
                displayToast("Project Removed: " + removeProjName);
                Project.removeProject(removeProjName);
                activitiesList = Project.getActivitiesList();
            }
        } else if(requestCode==3) {
            try {
                displayToast("Added new time");
                String finalUsername = data.getExtras().get("finalUsername").toString();
                String finalProject = data.getExtras().get("finalProject").toString();
                String finalStartTimeHour = data.getExtras().get("finalStartTimeHour").toString();
                String finalStartTimeMin = data.getExtras().get("finalStartTimeMin").toString();
                String finalEndTimeHour = data.getExtras().get("finalEndTimeHour").toString();
                String finalEndTimeMin = data.getExtras().get("finalEndTimeMin").toString();
                String finalStartDay = data.getExtras().get("finalStartDay").toString();
                String finalStartMonth = data.getExtras().get("finalStartMonth").toString();
                String finalStartYear = data.getExtras().get("finalStartYear").toString();
                String finalEndDay = data.getExtras().get("finalEndDay").toString();
                String finalEndMonth = data.getExtras().get("finalEndMonth").toString();
                String finalEndYear = data.getExtras().get("finalEndYear").toString();

                Date dateStart = new Date(Integer.parseInt(finalStartYear), Integer.parseInt(finalStartMonth), Integer.parseInt(finalStartDay), Integer.parseInt(finalStartTimeHour), Integer.parseInt(finalStartTimeMin));
                Date dateEnd = new Date(Integer.parseInt(finalEndYear), Integer.parseInt(finalEndMonth), Integer.parseInt(finalEndDay), Integer.parseInt(finalEndTimeHour), Integer.parseInt(finalEndTimeMin));
                Log.d("addingTimesTester", Integer.toString(dateStart.getYear()) + " : " + Integer.toString(dateStart.getMonth()) + " : " + Integer.toString(dateStart.getDate()) + " : " + Integer.toString(dateStart.getHours()) + " : " + Integer.toString(dateStart.getMinutes()) + " : " + Integer.toString(dateStart.getSeconds()));
                Log.d("addingTimesTester", Integer.toString(dateEnd.getYear()) + " : " + Integer.toString(dateEnd.getMonth()) + " : " + Integer.toString(dateEnd.getDate()) + " : " + Integer.toString(dateEnd.getHours()) + " : " + Integer.toString(dateEnd.getMinutes()) + " : " + Integer.toString(dateStart.getSeconds()));

                TimeEntry manualTimeEntry = new TimeEntry(finalProject, finalUsername, dateStart, dateEnd, false);
            } catch (NullPointerException e) {
                displayToast(getString(R.string.NoTimeError));
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (timerIsRunning() && !is_starting_up)
            stopTimer();
        is_starting_up = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        throw new RuntimeException("Nothing selected!");
    }
}

