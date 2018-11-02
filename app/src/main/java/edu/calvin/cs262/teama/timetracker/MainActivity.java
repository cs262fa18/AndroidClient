package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinActivities;
    String[] activitiesList = {"Project Alpha", "Project Beta", "Project Gamma", "Project Zeta"};

    private int seconds;
    private ImageView playPause;
    private TextView timerText;
    private TimeEntry current_time_entry;

    public static CSVImportExport csv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        startSpinner();
        timerText = (TextView)findViewById(R.id.timerText);
        playPause = (ImageView)findViewById(R.id.play);

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
                current_time_entry = null;
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

                    if (current_time_entry == null && te.getEndTime() == null) {
                        current_time_entry = te;
                        playPause.setImageResource(R.drawable.start);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read information from last time into current application
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

        } else if (id == R.id.remove_project) {

        } else if (id == R.id.manual_time_entry) {

        } else if (id == R.id.manual_time_removal) {

        } else if (id == R.id.dark_theme_switch) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

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
        current_time_entry = new TimeEntry("TestProject", "TestUser", new Date(), null, false);
        Log.d("CS262", "Starting timer");
    }

    private void stopTimer() {
        playPause.setImageResource(R.drawable.play);
        current_time_entry.setEndTime(new Date());
        current_time_entry = null;
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

    private String getElapsedTime() {
        long millis = new Date().getTime() - current_time_entry.getStartTime().getTime();
        int seconds_passed_total = ((int) millis) / 1000;
        int seconds_passed_partial = seconds_passed_total % 60;
        int minutes_passed_total = seconds_passed_total / 60;
        int minutes_passed_partial = minutes_passed_total % 60;
        int hours_passed = seconds_passed_total / 60;
        return String.format("%d:%02d:%02d", hours_passed, minutes_passed_partial, seconds_passed_partial);
    }

    public void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(timerIsRunning())
                    timerText.setText(getElapsedTime());
                handler.post(this);
            }
        });
    }

    private boolean timerIsRunning() {
        return (this.current_time_entry != null);
    }
}

