package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
//import com.example.weonseok.timetracker.R;

=======
>>>>>>> 1e7bae86a26420eec33e6663deedc792abd36ef5
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinActivities;
    String[] activitiesList = {"Project Alpha", "Project Beta", "Project Gamma", "Project Zeta"};

    int seconds;
    boolean running;
    boolean clicked;
    ImageView playPause;
    TextView savedTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSpinner();
        clicked = false;
        playPause = (ImageView)findViewById(R.id.play);
        savedTimes = (TextView)findViewById(R.id.savedTimes);
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
        startTimer(null);
        showMsg("Start time!");
    }

    public void showMsg(String message) {
        displayToast(message);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startTimer(View view){
        if (!clicked) {
            playPause.setImageResource(R.drawable.start);
            clicked = true;
            running = true;
        } else if (clicked){
            playPause.setImageResource(R.drawable.play);
            clicked = false;
            running = false;
        } else {
            clicked = false;
            running = false;
            seconds = 0;
        }
    }

    public void resetTimer(View view){
        running = false;
        String projectName = spinActivities.getSelectedItem().toString();
        if (savedTimes.length() == 0) {
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int sec = seconds % 60;
            String time = projectName + ": " + String.format("%d:%02d:%02d", hours, minutes, sec);
            savedTimes.setText(time);
        } else {
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int sec = seconds % 60;
            String time = projectName + ": " + String.format("%d:%02d:%02d", hours, minutes, sec);
            String timeRevised = time + "\n" + savedTimes.getText();
            savedTimes.setText(timeRevised);
        }
        seconds = 0;
        playPause.setImageResource(R.drawable.play);
        clicked = false;
    }

    public void runTimer(){
        final TextView timerText = (TextView)findViewById(R.id.timerText);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, sec);
                timerText.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}

