package edu.calvin.cs262.teama.timetracker;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<String> {

    private Spinner spinActivities;
    private ArrayList<String> activitiesList = new ArrayList<String>();
    private TextView usernameTextView;

    private ImageView playPause;
    private TextView timerText;
    private TextView todaysTimeText;
    private TimeEntry current_time_entry;
    private boolean is_starting_up;
    private boolean crashed = false;
    public static CSVImportExport csv;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            csv = new CSVImportExport(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
            crashed = true;
        }

        try {
            if (!crashed) {
                if (csv.getUsernameCSVFile().exists()) {
                    // Import data from csv
                    FileInputStream fis = new FileInputStream(csv.getUsernameCSVFile());
                    String[] imported_data = csv.importUsernameCSV(fis);
                    fis.close();

                    // Clear current list of time entries
                    userName = null;
                    ProjectUsername.removeUsername();
                    ProjectUsername.setUsername(imported_data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if (ProjectUsername.getUsername() == "" || ProjectUsername.getUsername() == null) {
            Intent intent = new Intent(this, signInPage.class);
            startActivityForResult(intent, 5);
        }

        userName = ProjectUsername.getUsername();

        try {
            if (!crashed) {
                if (csv.getProjectsCSVFile().exists()) {
                    // Import data from csv
                    FileInputStream fis = new FileInputStream(csv.getProjectsCSVFile());
                    String[] imported_data = csv.importProjectsCSV(fis);
                    fis.close();

                    // Clear current list of time entries
                    activitiesList = null;
                    ProjectUsername.removeAllProjects();

                    for (int i = 1; i < imported_data.length; i++) {
                        // Start at 1, because we don't want to use the header row as data
                        String project;

                        project = imported_data[i];
                        ProjectUsername.addProject(project);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ProjectUsername.getActivitiesList().isEmpty()) {
            ProjectUsername.addProject("Test Project 1");
            ProjectUsername.addProject("Test Project 2");
            ProjectUsername.addProject("Test Project 3");
            ProjectUsername.addProject("Test Project 4");
            ProjectUsername.addProject("Test Project 5");
        }

        activitiesList = ProjectUsername.getActivitiesList();


        setContentView(R.layout.activity_main);
        timerText = (TextView)findViewById(R.id.timerText);
        todaysTimeText = (TextView)findViewById(R.id.todaysTimeText);
        playPause = (ImageView)findViewById(R.id.play);
        is_starting_up = true;

        usernameTextView=(TextView)findViewById(R.id.userNameDisplay);
        usernameTextView.setText("Welcome Back " + ProjectUsername.getUsername());

        // Create data storage csv object


        try {
            if (!crashed) {
                Log.i("CSV File exists", new Boolean(csv.getTimesCSVFile().exists()).toString());
                Log.i("CSV File path", csv.getTimesCSVFile().getAbsolutePath());
                Log.i("External files dir", getApplicationContext().getExternalFilesDir(null).getAbsolutePath());

                if (csv.getTimesCSVFile().exists()) {
                    // Import data from csv
                    FileInputStream fis = new FileInputStream(csv.getTimesCSVFile());
                    String[][] imported_data = csv.importTimesCSV(fis);
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
                            Log.d("CurrTime", "Setting index " + i);
                            current_time_entry = te;
                            playPause.setImageResource(R.drawable.start);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Bundle postBundle = new Bundle();
//        postBundle.putString("username", "Billy Boy Boi");
//        postBundle.putString("projectName", "Leema");
//        postBundle.putString("managerID", "5");
//        postBundle.putString("startTime", "2018-10-25T14:31:29.000Z");
//        postBundle.putString("endTime", "2018-10-26T10:06:48.000Z");
//        postBundle.putString("employeeID", "8");
//        postBundle.putString("projectID", "2");
//        postBundle.putString("newUsername", "Billy Boy Boi");
//        postBundle.putString("newProjectName", "Leema");
//        postBundle.putString("newManagerID", "5");
//        postBundle.putString("newStartTime", "2018-10-25T14:31:29.000Z");
//        postBundle.putString("newEndTime", "2018-10-26T10:06:48.000Z");
//        postBundle.putString("newEmployeeID", "8");
//        postBundle.putString("newProjectID", "2");
//        postBundle.putString("userIdToChange", "2");
//        postBundle.putString("projIdToChange", "2");
//        postBundle.putString("timeIdToChange", "2");
//
//        putData(3, postBundle);
//        postData(1, postBundle);
//        getData(0);

        startSpinner();
        runTimer();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (NullPointerException e) {
            super.onBackPressed();
        }

        updateTimes();
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

        ProjectUsername.saveSelectedProject(spinActivities.getSelectedItemPosition());

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
            Intent intent = new Intent(this, removeTimes.class);
            startActivityForResult(intent, 6);

        } else if (id == R.id.view_times) {
            ArrayList<String> projectNames = new ArrayList<String>();
            ArrayList<Float> projectTime = new ArrayList<Float>();

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

                startActivity(intent);
            } else { displayToast("Error"); }

        } else if (id == R.id.dark_theme_switch) {

        } else if (id == R.id.log_out) {
            userName = "";
            ProjectUsername.removeUsername();
            Intent intent = new Intent(this, signInPage.class);
            startActivityForResult(intent, 5);

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
        if (timerIsRunning())
            spinActivities.setSelection(ProjectUsername.getActivitiesList().indexOf(current_time_entry.getProject()));
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
        current_time_entry = new TimeEntry((String) spinActivities.getSelectedItem(), userName, new Date(), null, false);
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
        int projRemoved = -1;
        if((requestCode==2) & (resultCode==2))
        {
            try {
                String newProjName = data.getExtras().get("addedProj").toString();
                if (newProjName.isEmpty()) {
                    displayToast(getString(R.string.addEmptyProjectError));
                } else {
                    displayToast("New Project Added: " + newProjName);
                    ProjectUsername.addProject(newProjName);
                    activitiesList = ProjectUsername.getActivitiesList();
                }
            } catch (NullPointerException e) {
                displayToast("No Project Added");
            }
            Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
            saveAndSyncThread.start();
        } else if((requestCode==2) & (resultCode==3)) {
            try {
                String removeProjName = data.getExtras().get("removeProj").toString();
                if (removeProjName.isEmpty()) {
                    displayToast(getString(R.string.addEmptyProjectError));
                } else {
                    displayToast("Project Removed: " + removeProjName);
                    projRemoved = ProjectUsername.removeProject(removeProjName);
                    activitiesList = ProjectUsername.getActivitiesList();
                }
            } catch (NullPointerException e) {
                displayToast("No project removed");
            }
            Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
            saveAndSyncThread.start();
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

            updateTimes();
            Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
            saveAndSyncThread.start();
        } else if (requestCode == 5) {
            try {
                if (timerIsRunning()) {
                    stopTimer();
                }
                ProjectUsername.setUsername(data.getExtras().get("username").toString());
                userName = ProjectUsername.getUsername();
                usernameTextView.setText("Welcome Back " + ProjectUsername.getUsername());
                Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
                saveAndSyncThread.start();
            } catch (NullPointerException e) {
                Intent intent = new Intent(this, signInPage.class);
                startActivityForResult(intent, 5);
            }
    } else if (requestCode == 6) {
            int position = Integer.parseInt(data.getExtras().get("position").toString());
            TimeEntry.removeTimeEntry(position);
            Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
            saveAndSyncThread.start();
            displayToast("Time Removed");
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


    /**
     * The Next few fumctions are for the loader
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new DataLoader(getApplicationContext(), args.getString("queryString"), args.getString("method"), args.getBundle("bundleOfData"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(data);
            // Get the JSONArray of book items.
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//
//            // Initialize iterator and results fields.
//            int i = 0;
//            String title = null;
//
//            // Look for results in the items array, exiting when both the title and author
//            // are found or when all items have been checked.
//            while (i < itemsArray.length() || (title == null)) {
//                // Get the current item information.
//                JSONObject player = itemsArray.getJSONObject(i);
//
//                // Try to get the author and title from the current item,
//                // catch if either field is empty and move on.
//                try {
//                    String id = player.getString("id");
//                    String eMail = player.getString("emailAddress");
//                    String name = null;
//                    try {
//                        name = player.getString("name");
//                    } catch (Exception e) {
//                        name = "No Name";
//                    }
//                    if (title == null) {
//                        title = id + ", " + name + ", " + eMail;
//                    } else {
//                        title = title + "\n" + id + ", " + name + ", " + eMail;
//                    }
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                // Move to the next item.
//                i++;
//            }
//
//            // If both are found, display the result.
//            if (title != null){
////                mPlayerText.setText(title);
////                mPlayerInput.setText("");
//            } else {
//                // If none are found, update the UI to show failed results.
//                displayToast("No results found");
//            }

        } catch(Exception e) {
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                String info = null;
//                String id = jsonObject.getString("id");
//                String eMail = jsonObject.getString("emailAddress");
//                String name = null;
//                try {
//                    name = jsonObject.getString("name");
//                } catch (Exception q) {
//                    name = "No Name";
//                }
//                info = id + ", " + name + ", " + eMail;
//
//                if (info != null) {
////                    mPlayerText.setText(info);
////                    mPlayerInput.setText("");
//                } else {
//                    // If none are found, update the UI to show failed results.
//                    displayToast("No results found");
//                }
//            } catch (Exception q) {
//                // If onPostExecute does not receive a proper JSON string, update the UI to show failed results.
//                displayToast("Please enter nothing or a valid ID number.");
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {}

    public void getData(int queryInt) {
        if (queryInt <= 3 && queryInt >= 0) {
//        String queryString = mPlayerInput.getText().toString();
//        if (queryString.toString().length() == 0) {
//            queryString = "-1";
//        }


        /*
        0 returns everything
        1 returns times
        2 returns projects
        3 returns usernames
         */
            String queryString = Integer.toString(queryInt);

//        try {
//            InputMethodManager inputManager = (InputMethodManager)
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        } catch (Exception e) { }

            if (getSupportLoaderManager().getLoader(0) != null) {
                getSupportLoaderManager().initLoader(0, null, this);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                queryBundle.putString("method", "getData");
                getSupportLoaderManager().restartLoader(0, queryBundle, this);
            } else {
                if (queryString.length() == 0) {
                    displayToast("Please enter a search term");
                } else {
                    displayToast("Please check your network connection and try again.");
                }
            }
        } else {displayToast("Invalid get query number"); }
        displayToast("Ran get data");
    }

    public void postData(int queryInt, Bundle data) {
        if (queryInt <= 3 && queryInt >= 1) {
//        String queryString = mPlayerInput.getText().toString();
//        if (queryString.toString().length() == 0) {
//            queryString = "-1";
//        }


        /*
        1 post to times
        2 post to projects
        3 post to usernames
         */
            String queryString = Integer.toString(queryInt);

//        try {
//            InputMethodManager inputManager = (InputMethodManager)
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        } catch (Exception e) { }

            if (getSupportLoaderManager().getLoader(0) != null) {
                getSupportLoaderManager().initLoader(0, null, this);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                queryBundle.putString("method", "postData");
                queryBundle.putBundle("bundleOfData", data);
                getSupportLoaderManager().restartLoader(0, queryBundle, this);
            } else {
                if (queryString.length() == 0) {
                    displayToast("Please enter a search term");
                } else {
                    displayToast("Please check your network connection and try again.");
                }
            }
        } else {displayToast("Invalid get query number"); }
        displayToast("Ran post data");
    }

    public void putData(int queryInt, Bundle data) {
        if (queryInt <= 3 && queryInt >= 1) {
//        String queryString = mPlayerInput.getText().toString();
//        if (queryString.toString().length() == 0) {
//            queryString = "-1";
//        }


        /*
        1 post to times
        2 post to projects
        3 post to usernames
         */
            String queryString = Integer.toString(queryInt);

//        try {
//            InputMethodManager inputManager = (InputMethodManager)
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        } catch (Exception e) { }

            if (getSupportLoaderManager().getLoader(0) != null) {
                getSupportLoaderManager().initLoader(0, null, this);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                queryBundle.putString("method", "putData");
                queryBundle.putBundle("bundleOfData", data);
                getSupportLoaderManager().restartLoader(0, queryBundle, this);
            } else {
                if (queryString.length() == 0) {
                    displayToast("Please enter a search term");
                } else {
                    displayToast("Please check your network connection and try again.");
                }
            }
        } else {displayToast("Invalid get query number"); }
        displayToast("Ran post data");
    }
}

