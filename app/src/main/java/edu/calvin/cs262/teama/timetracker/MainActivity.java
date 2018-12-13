package edu.calvin.cs262.teama.timetracker;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
//        AdapterView.OnItemSelectedListener,
        LoaderManager.LoaderCallbacks<String> {

    public static CSVImportExport csv;
    public boolean loaderFinished = true;
    private Spinner spinActivities;
    private ArrayList<String> activitiesList = new ArrayList<String>();
    private TextView usernameTextView;
    private ImageView playPause;
    private TextView timerText;
    private TextView todaysTimeText;
    private TimeEntry current_time_entry;
    private boolean is_starting_up;
    private boolean crashed = false;
    private int userNameID;
    private boolean checkpass = false;
    private String password;
    private String username;
    private int timeID = -1;
    private String UUIDget = "";
    private int loadID = 0;
    private boolean newUserEntered = false;
    private String enterNewUsername;
    private boolean grabNames = false;
    private boolean viewTimes = false;
    private boolean justReturnedFromActivity = false;


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
                    ProjectUsername.setUsernameID(Integer.parseInt(imported_data[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        try {
            if (ProjectUsername.getUsernameID() < 0 && !checkpass && !newUserEntered) {
                Intent intent = new Intent(this, signInPage.class);
                startActivityForResult(intent, 5);
            }
        } catch (NullPointerException e) {
            Intent intent = new Intent(this, signInPage.class);
            startActivityForResult(intent, 5);
            e.printStackTrace();
        }

        userNameID = ProjectUsername.getUsernameID();

        if (networkInfo != null && networkInfo.isConnected() && !checkpass && !newUserEntered && (ProjectUsername.getUsernameID() > 0)) {
            // Log.d("Quentin", "FALSEGET1");
            grabNames = true;
            getData(2);
        }

//        try {
//            if (!crashed) {
//                if (csv.getProjectsCSVFile().exists()) {
//                    Log.d("LOG_TAG1", "Running project Csv1");
//
//                    // Import data from csv
//                    FileInputStream fis = new FileInputStream(csv.getProjectsCSVFile());
//                    Object[][] imported_data = csv.importProjectsCSV(fis);
//                    fis.close();
//                    Log.d("LOG_TAG1", "Running project Csv2");
//
//                    // Clear current list of time entries
//                    activitiesList.clear();
//                    Log.d("LOG_TAG1", "Running project Csv3");
//                    ProjectUsername.removeAllProjects();
//                    Log.d("LOG_TAG1", "Running project Csv4");
//
//                    for (int i = 1; i < imported_data.length; i++) {
//                        // Start at 1, because we don't want to use the header row as data
//                        Log.d("LOG_TAG1", "Running project Csv");
//                        Log.d("LOG_TAG1", imported_data[i].toString() + imported_data[i][0].toString() + imported_data[i][1].toString() + imported_data[i][2].toString());
//
//                        ProjectUsername.addProject(imported_data[i][0].toString(), Integer.parseInt(imported_data[i][2].toString()), Integer.parseInt(imported_data[i][1].toString()));
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("LOG_TAG1", ProjectUsername.getActivitiesList().toString());
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//            Log.d("LOG_TAG1", "running get projects");
//            ProjectUsername.removeAllProjects();
//            Log.d("LOG_TAG1", "removed projects");
//
//            getData(2);
//        }

        setContentView(R.layout.activity_main);
        timerText = (TextView) findViewById(R.id.timerText);
        todaysTimeText = (TextView) findViewById(R.id.todaysTimeText);
        playPause = (ImageView) findViewById(R.id.play);
        is_starting_up = true;

        usernameTextView = (TextView) findViewById(R.id.userNameDisplay);
        usernameTextView.setText("Welcome Back " + ProjectUsername.getUsername(userNameID));

        // Create data storage csv object


//        try {
//            if (!crashed) {
//                Log.i("CSV File exists", new Boolean(csv.getTimesCSVFile().exists()).toString());
//                Log.i("CSV File path", csv.getTimesCSVFile().getAbsolutePath());
//                Log.i("External files dir", getApplicationContext().getExternalFilesDir(null).getAbsolutePath());
//
//                if (csv.getTimesCSVFile().exists()) {
//                    // Import data from csv
//                    FileInputStream fis = new FileInputStream(csv.getTimesCSVFile());
//                    String[][] imported_data = csv.importTimesCSV(fis);
//                    fis.close();
//
//                    // Clear current list of time entries
//                    current_time_entry = null;
//                    TimeEntry.clearTimeEntries();
//
//                    for (int i = 1; i < imported_data.length; i++) {
//                        // Start at 1, because we don't want to use the header row as data
//                        UUID uuid;
//                        String project;
//                        String username;
//                        Date time_start;
//                        Date time_end;
//                        int action;
//                        boolean synced;
//
//                        uuid = UUID.fromString(imported_data[i][0]);
//                        project = imported_data[i][1];
//                        username = imported_data[i][2];
//                        time_start = new Date(imported_data[i][3]);
//                        if (imported_data[i][4].equals("")) {
//                            time_end = null;
//                        } else {
//                            time_end = new Date(imported_data[i][4]);
//                        }
//                        synced = Boolean.parseBoolean(imported_data[i][5]);
//                        TimeEntry te = new TimeEntry(uuid, project, username, time_start, time_end, synced);
//
//                        // Uncomment the following to log reading of TimeEntries from the file
////                    Log.d("Start TE", "Index " + i);;
////                    Log.d("UUID", uuid.toString());
////                    Log.d("Project", project);
////                    Log.d("Username", username);
////                    Log.d("Time start", time_start.toString());
////                    if (time_end == null) {
////                        Log.d("Time end", "null");
////
////                    } else {
////                        Log.d("Time end", time_end.toString());
////                    }
////                    Log.d("Synced", Boolean.toString(synced));
////
////                    Log.d("Cond1",Boolean.toString(current_time_entry == null));
////                    Log.d("Cond1",Boolean.toString(current_time_entry == null));
//
//
//                        if (current_time_entry == null && te.getEndTime() == null) {
//                            Log.d("CurrTime", "Setting index " + i);
//                            current_time_entry = te;
//                            playPause.setImageResource(R.drawable.start);
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
        saveAndSyncThread.start();
        startSpinner();
        startNavView();
        runTimer();
    }

    @Override
    public void onBackPressed() {
        justReturnedFromActivity = true;
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
        grabNames = true;
        getData(2);
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
            if (timerIsRunning()) {
                stopTimer();
            }
            Intent intent = new Intent(this, removeTimes.class);
            startActivityForResult(intent, 6);

        } else if (id == R.id.view_times) {
            grabNames = true;
            viewTimes = true;
            // Log.d("Quentin", "FALSEGET2");
            getData(2);

        } else if (id == R.id.help_text) {
            Intent intent = new Intent(this, onLineHelpText.class);
            startActivity(intent);

        } else if (id == R.id.log_out) {
            userNameID = -1;
            ProjectUsername.removeUsernameID();
            userNameID = ProjectUsername.getUsernameID();
            Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
            saveAndSyncThread.start();
            checkpass = false;
            newUserEntered = false;
            username = null;
            password = null;
            enterNewUsername = null;
            Intent intent = new Intent(this, signInPage.class);
            startActivityForResult(intent, 5);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startSpinner() {
        spinActivities = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, ProjectUsername.getActivitiesListProject());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinActivities.setAdapter(adapter);
        if (timerIsRunning())
            spinActivities.setSelection(ProjectUsername.getActivitiesList().indexOf(current_time_entry.getProject()));
    }

    public void startNavView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void showStartMsg(View view) {
        toggleTimerRunning(null);
        showMsg("Start time!");
    }

    public void showMsg(String message) {
        displayToast(message);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startTimer() {
        if (ProjectUsername.getActivitiesList().isEmpty()) {
            // Log.d("Quentin", "DONT START THE TIMER");
        } else {
            playPause.setImageResource(R.drawable.start);
            current_time_entry = new TimeEntry((String) spinActivities.getSelectedItem(), ProjectUsername.getUsername(ProjectUsername.getUsernameID()), new Date(), null, false);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Bundle postBundle = new Bundle();
            postBundle.putString("startTime", newFormat.format(current_time_entry.getStartTime()));
            postBundle.putString("endTime", "null");
            postBundle.putString("employeeID", Integer.toString(ProjectUsername.getUsernameID()));
            postBundle.putString("projectID", Integer.toString(ProjectUsername.getProjectID(current_time_entry.getProject())));
            postBundle.putString("UUID", current_time_entry.getUUID().toString());

            // Log.d("startTime", newFormat.format(current_time_entry.getStartTime()));
            // Log.d("endTime", "null");
            // Log.d("employeeID", Integer.toString(ProjectUsername.getUsernameID()));
            // Log.d("projectID", Integer.toString(ProjectUsername.getProjectID(current_time_entry.getProject())));
            // Log.d("UUID", current_time_entry.getUUID().toString());

            ProjectUsername.setIsRunningPost(true);
            postData(1, postBundle);
            updateTimes();
            // Log.d("CS262", "Starting timer");
        }
    }

    private void stopTimer() {
        playPause.setEnabled(false);
        current_time_entry.setEndTime(new Date());
        UUIDget = current_time_entry.getUUID().toString();
        // Log.d("Quentin", "UUID " + UUIDget);
        timeID = -3;
        // Log.d("Quentin", "FALSEGET3");
        getData(1);
        // Log.d("CS262", "Stopping timer");
    }

    public void toggleTimerRunning(View view) {
        if (timerIsRunning()) {
            // Stop timer
            stopTimer();
        } else {
            // Start timer
            startTimer();
            spinActivities.setEnabled(false);
        }
        Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
        saveAndSyncThread.start();
    }

    public static String formatTime(long millis) {
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

    private String getTodaysElapsedTime() {
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

    public void runTimer() {
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
        if (timerIsRunning())
            timerText.setText(getElapsedTime());
        todaysTimeText.setText("Today: " + getTodaysElapsedTime());
    }

    private boolean timerIsRunning() {
        return (this.current_time_entry != null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle postBundle = new Bundle();
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        int projRemoved = -1;
        if ((requestCode == 2) & (resultCode == 2)) {
            try {
                String newProjName = data.getExtras().get("addedProj").toString();
                if (newProjName.isEmpty()) {
                    displayToast(getString(R.string.addEmptyProjectError));
                } else {
                    boolean nameExists = false;
                    for (Object[] o : ProjectUsername.getActivitiesList()) {
                        if (o[1].toString().matches(newProjName)) {
                            nameExists = true;
                        }
                    }
                    if (!nameExists) {
                        displayToast("New Project Added: " + newProjName);
                        postBundle.putString("projectName", newProjName);
                        postBundle.putString("managerID", Integer.toString(ProjectUsername.getUsernameID()));

                        // Log.d("Quentin", "ITS BREAKING IF YOU SEE THIS MORE THAN ONCE");
                        ProjectUsername.setIsRunningPost(true);
                        postData(2, postBundle);
                    } else {
                        displayToast("Project already exists");
                    }
                }
            } catch (NullPointerException e) {
                displayToast("No Project Added");
            }
        } else if ((requestCode == 2) & (resultCode == 3)) {
            // Log.d("Quentin", "Running remove project");
            try {
                String removeProjName = data.getExtras().get("removeProj").toString();
                if (removeProjName.isEmpty()) {
                    // Log.d("Quentin", "ITS BREAKING IF YOU SEE THIS MORE THAN ONCE");
                    displayToast(getString(R.string.removeEmptyProjectError));
                } else {
                    displayToast("Project Removed: " + removeProjName);
                    Bundle deleteBundle = new Bundle();
                    deleteBundle.putString("projIdToDelete", Integer.toString(ProjectUsername.getProjectID(removeProjName)));
                    // Log.d("Quentins", "delete id" + Integer.toString(ProjectUsername.getProjectID(removeProjName)));
                    // Log.d("Quentin", "ITS BREAKING IF YOU SEE THIS MORE THAN ONCE");
                    deleteData(2, deleteBundle);
                }
            } catch (NullPointerException e) {
                displayToast("No project removed");
            }
        } else if (requestCode == 3) {
            try {
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
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

                // Log.d("addingTimesTester", data.getExtras().get("finalUsername").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalProject").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalStartTimeHour").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalStartTimeMin").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalEndTimeHour").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalEndTimeMin").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalStartDay").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalStartMonth").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalStartYear").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalEndDay").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalEndMonth").toString());
                // Log.d("addingTimesTester", data.getExtras().get("finalEndYear").toString());

                String dateStartString = finalStartYear + "-" + finalStartMonth + "-" + finalStartDay + "-" + finalStartTimeHour + "-" + finalStartTimeMin + "-00";
                String dateEndString = finalEndYear + "-" + finalEndMonth + "-" + finalEndDay + "-" + finalEndTimeHour + "-" + finalEndTimeMin + "-00";
                // Log.d("addingTimesTester", dateStartString);
                // Log.d("addingTimesTester", dateEndString);

                TimeEntry manualTimeEntry = new TimeEntry(finalProject, finalUsername, newFormat.parse(dateStartString), newFormat.parse(dateEndString), false);


                postBundle.putString("startTime", newFormat.format(manualTimeEntry.getStartTime()));
                postBundle.putString("endTime", newFormat.format(manualTimeEntry.getEndTime()));
                postBundle.putString("employeeID", Integer.toString(ProjectUsername.getUsernameID()));
                postBundle.putString("projectID", Integer.toString(ProjectUsername.getProjectID(manualTimeEntry.getProject())));
                postBundle.putString("UUID", manualTimeEntry.getUUID().toString());

                // Log.d("Quentin", "ITS BREAKING IF YOU SEE THIS MORE THAN ONCE");
                ProjectUsername.setIsRunningPost(true);
                postData(1, postBundle);
            } catch (NullPointerException e) {
                displayToast(getString(R.string.NoTimeError));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            updateTimes();
        } else if (requestCode == 5 && resultCode == 1) {
            if (data.getExtras().get("status").toString().matches("SIGNIN")) {
                try {
                    // Log.d("Quentin", "SIGNIN CODE");
                    String newUsername = data.getExtras().get("username").toString();
                    String newPassword = data.getExtras().get("password").toString();
                    checkpass = true;
                    username = newUsername;
                    password = newPassword;
                    // Log.d("Quentin", "FALSEGET4");
                    // Log.d("Quentin", "ITS BREAKING IF YOU SEE THIS MORE THAN ONCE");
                    getData(3);

                } catch (NullPointerException e) {
                    Intent intent = new Intent(this, signInPage.class);
                    startActivityForResult(intent, 5);
                }
            }
        } else if (requestCode == 5 && resultCode == 2) {
            if (data.getExtras().get("status").toString().matches("REGISTER")) {
                try {
                    // Log.d("Quentin", "REGISTER CODE");
                    String newUsername = data.getExtras().get("username").toString();
                    String newPassword = data.getExtras().get("password").toString();
                    boolean nameExists = false;
                    for (Object[] o : ProjectUsername.getUsernameList()) {
                        if (o[0].toString().matches(newUsername)) {
                            nameExists = true;
                        }
                    }
                    if (!nameExists) {
                        postBundle.putString("username", newUsername);
                        postBundle.putString("password", newPassword);
                        // Log.d("username", newUsername);
                        // Log.d("password", newPassword);
//                        newUserEntered = true;
                        enterNewUsername = newUsername;

                        // Log.d("Quentin", "ITS BREAKING IF YOU SEE THIS MORE THAN ONCE");
                        ProjectUsername.setIsRunningPost(true);
                        postData(3, postBundle);
                    } else {
                        displayToast("Username already exists");
                        Intent intent = new Intent(this, signInPage.class);
                        startActivityForResult(intent, 5);
                    }
                } catch (NullPointerException e) {
                    Intent intent = new Intent(this, signInPage.class);
                    startActivityForResult(intent, 5);
                }
            }
        } else if (requestCode == 6) {
            int position = Integer.parseInt(data.getExtras().get("position").toString());
            int newPosition = (TimeEntry.getAllTimeEntries().size() - position) - 1;
            UUIDget = TimeEntry.getAllTimeEntries().get(newPosition).getUUID().toString();
            // Log.d("Quentin", "UUID " + UUIDget);
            timeID = -2;
            // Log.d("Quentin", "FALSEGET5");
            getData(1);
            displayToast("Time Removed");
        }
    }


    /**
     * The Next few fumctions are for the loader
     *
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
            // Log.d("Quentins Log", "10");
            String[] newData;
            try {
                newData = data.split("#@!BREAK!@#");
            } catch (Exception e) {
                e.printStackTrace();
                newData = new String[] {"SplitError"};
            }
            // Log.d("Quentins Log", newData[0]);
            if (!(newData.length == 1)) {
                if (newData[0].matches("TimesGetData")) {
                    // Log.d("Quentins Log", "11");
                    parseTimes(newData);

                } else if (newData[0].matches("ProjectGetData")) {
                    parseProjects(newData);

                } else if (newData[0].matches("UserGetData")) {
                    parseUsernames(newData);

                } else if (newData[0].matches("UserPostSucsessful")) {
                    // Log.d("Quentin", "Running123321 User Get From Post");
                    // Log.d("Quentin", "FALSEGET10");
                    newUserEntered = true;
                    getData(3);
                } else if (newData[0].matches("ProjPostSucsessful")) {
                    // Log.d("Quentin", "Running123321 Project Get From Post");
                    // Log.d("Quentin", "FALSEGET11");
                    grabNames = true;
                    getData(2);
                } else if (newData[0].matches("ProjDelSucsessful")) {
                    // Log.d("Quentin", "Running123321 Project Delete From Post");
                    // Log.d("Quentin", "FALSEGET12");
                    grabNames = true;
                    getData(2);
                } else if (newData[0].matches("TimeDelSucsessful")) {
                    // Log.d("Quentin", "Running123321 Time Delete From get");
                    grabNames = true;
                    // Log.d("Quentin", "FALSEGET13");
                    getData(2);
                } else if (newData[0].matches("TimePostSucsessful")) {
                    // Log.d("Quentin", "Running123321 Time Delete From get");
                    grabNames = true;
                    getData(2);
                } else if (newData[0].matches("TimePutSucsessful")) {
                    // Log.d("Quentin", "Running123321 Time Delete From get");
                    current_time_entry = null;
                    updateTimes();
                    grabNames = true;
                    // Log.d("Quentin", "FALSEGET14");
                    getData(2);
                }
                // Log.d("Quentins Log", "15");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread saveAndSyncThread = new Thread(new SaveAndSyncManager());
        saveAndSyncThread.start();
    }

    public void parseTimes(String[] newData) {
        // Log.d("BadTime", "parseTimes: run1");
        TimeEntry.clearTimeEntries();
        current_time_entry = null;
        try {
            JSONObject jsonObject = new JSONObject(newData[1]);
            JSONArray timesArray = jsonObject.getJSONArray("items");

            int i = 0;
//            ArrayList<String[]> timesFromCloud = new ArrayList<String[]>();
            while (i < timesArray.length() || (TimeEntry.getAllTimeEntries().isEmpty())) {
                // Get the current item information.
                JSONObject times = timesArray.getJSONObject(i);

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    String id = times.getString("id");
                    String startTime = times.getString("startTime");
                    String endTime = times.getString("endTime");
                    String employeeID = times.getString("employeeID");
                    String projectID = times.getString("projectID");
                    String uuid = times.getString("uuid");
                    // Log.d("Quentins Log", "12");

                    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

                    if (!endTime.matches("null")) {
                        TimeEntry newTimeEntryFromServer = new TimeEntry(
                                UUID.fromString(uuid),
                                ProjectUsername.getProjectName(Integer.parseInt(projectID)),
                                ProjectUsername.getUsername(Integer.parseInt(employeeID)),
                                newFormat.parse(startTime),
                                newFormat.parse(endTime),
                                true);
                    } else {
                        TimeEntry newTimeEntryFromServer = new TimeEntry(
                                UUID.fromString(uuid),
                                ProjectUsername.getProjectName(Integer.parseInt(projectID)),
                                ProjectUsername.getUsername(Integer.parseInt(employeeID)),
                                newFormat.parse(startTime),
                                null,
                                true);
                        if (ProjectUsername.getUsernameID() == Integer.parseInt(employeeID)) {
                            // Log.d("CurrTime", "Setting index " + i);
                            current_time_entry = newTimeEntryFromServer;
                            String projectName = ProjectUsername.getProjectName(Integer.parseInt(projectID));
                            int p = 0;
                            for (String s : ProjectUsername.getActivitiesListProject()) {
                                if (s.matches(projectName)) {
                                    spinActivities.setSelection(p);
                                }
                                p++;
                            }
                        }
                    }

                    if (timeID == -2 && times.getString("uuid").matches(UUIDget)) {
                        timeID = Integer.parseInt(times.getString("id"));
                        Bundle deleteBundle = new Bundle();
                        deleteBundle.putString("timeIdToDelete", Integer.toString(timeID));
                        deleteData(1, deleteBundle);
                    }
                    // Log.d("addingTimesTester", Integer.toString(timeID) + ": " + times.getString("uuid") + ": " + UUIDget);
                    if (timeID == -3 && times.getString("uuid").matches(UUIDget) && !viewTimes) {
                        // Log.d("addingTimesTester", "MATCH");
//                        Log.d("BadTime", "PutEndTime: " + newFormat.format(current_time_entry.getEndTime()) + " to " + times.getString("id"));
                        timeID = Integer.parseInt(times.getString("id"));
                        Bundle putBundle = new Bundle();
                        // Log.d("addingTimesTester", "MATCH");
                        putBundle.putString("newEndTime", newFormat.format(new Date()));
                        putBundle.putString("timeIdToChange", Integer.toString(timeID));
                        // Log.d("addingTimesTester", "MATCH");
                        putData(1, putBundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    displayToast("No results found");
                }

                // Move to the next item.
                i++;
            }
            // Log.d("BadTime", "parseTimes: run1.2");
            // Log.d("Quentins Log", "15.5");
        } catch (JSONException e) {
            e.printStackTrace();
            // Log.d("Quentins Log", "13.E");
        }
        // Log.d("BadTime", "parseTimes: run1.4");
        if (viewTimes) {
            // Log.d("BadTime", "parseTimes: run2");
            ArrayList<String> projectNames = new ArrayList<String>();
            ArrayList<Float> projectTime = new ArrayList<Float>();
            // Log.d("BadTime", "parseTimes: run2.2");
            int j = 0;
            for (String s: ProjectUsername.getActivitiesListProject()) {
                try {
                    Log.d("BadTime", "parseTimes: run2.3");
                    projectNames.add(s);
                    projectTime.add(Float.parseFloat(TimeEntry.getProjectTime(s)));
                    Log.d("BadTime", projectNames.get(j) + " : " + projectTime.get(j));
                    j++;
                } catch (Exception e) {
                    Log.d("BadTime", "parseTimes: run2.E");
                    Log.d("BadTime", e.toString());
                }
            }
            Log.d("BadTime", "parseTimes: run3");


            if (projectTime.size() == projectNames.size()) {
                Log.d("BadTime", "parseTimes: run4");
                Intent intent = new Intent(this, viewTimes.class);
                for (int i = 0; i < projectTime.size(); i++) {
                    Log.d("BadTime", "parseTimes: run4.1");
                    intent.putExtra("projectName" + i, projectNames.get(i));
                    intent.putExtra("projectTime" + i, projectTime.get(i));
                }
                intent.putExtra("size", projectTime.size());
                Log.d("BadTime", "parseTimes: run4.2");

                startActivity(intent);
            } else {
                displayToast("Error");
            }
            Log.d("BadTime", "parseTimes: run5");
            viewTimes = false;
        }
        if (timerIsRunning()) {
            playPause.setEnabled(true);
            playPause.setImageResource(R.drawable.start);
            spinActivities.setEnabled(false);
        } else {
            playPause.setEnabled(true);
            playPause.setImageResource(R.drawable.play);
            spinActivities.setEnabled(true);
        }
    }

    public void parseProjects(String[] newData) {
        Log.d("BadTime", "parseProjects: run1");
        try {
            ProjectUsername.removeAllProjects();
            JSONObject jsonObject = new JSONObject(newData[1]);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            Log.d("Quentins Log", "Projects1");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            Log.d("Quentins Log", "Projects2");

            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() || ProjectUsername.getActivitiesList().isEmpty()) {
                // Get the current item information.
                JSONObject player = itemsArray.getJSONObject(i);
                Log.d("Quentins Log", "Projects3");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    String projectName = player.getString("name");
                    int projectID = Integer.parseInt(player.getString("id"));
                    int managerID = Integer.parseInt(player.getString("managerID"));
                    ProjectUsername.addProject(projectName, managerID, projectID);

                } catch (Exception e) {
                    Log.d("Quentins Log", e.toString());
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }
            Log.d("Quentins Log", "Projects4");
            activitiesList = ProjectUsername.getActivitiesListProject();
            startSpinner();

        } catch (JSONException e) {
            Log.d("Quentins Log", e.toString());
            e.printStackTrace();
            activitiesList = ProjectUsername.getActivitiesListProject();
            startSpinner();
        }
        if (grabNames) {
            Log.d("BadTime", "parseProjects: run2");
            Log.d("Quentin", "FALSEGET15");
            getData(3);
        }
        Log.d("Quentins Log", "Projects5");
    }

    public void parseUsernames(String[] newData) {
        ProjectUsername.removeAllUsernames();
        try {
            JSONObject jsonObject = new JSONObject(newData[1]);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            Log.d("Quentins Log", "User1");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            Log.d("Quentins Log", "User2");

            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() || ProjectUsername.getUsernameList().isEmpty()) {
                    // Get the current item information.
                    JSONObject player = itemsArray.getJSONObject(i);
                    Log.d("Quentins Log", "User3");

                    // Try to get the author and title from the current item,
                    // catch if either field is empty and move on.
                    try {
                        int userID = Integer.parseInt(player.getString("id"));
                        String username = player.getString("username");
                        ProjectUsername.addUsername(username, userID);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Quentins Log", "username Crash");
                    }

                    if (checkpass && player.getString("username").matches(username) && player.getString("password").matches(password)) {
                        ProjectUsername.setUsernameID(Integer.parseInt(player.getString("id")));
                        checkpass = false;
                        username = null;
                        password = null;
                        grabNames = true;
                        Log.d("Quentin", "FALSEGET16");
                        getData(2);
                    }

                    Log.d("Quentin", Boolean.toString(newUserEntered) + ": " + player.getString("username") + ": " + enterNewUsername);
                    if (newUserEntered && player.getString("username").matches(enterNewUsername)) {
                        Log.d("Quentin", "Running User Get From Post2");
                        ProjectUsername.setUsernameID(Integer.parseInt(player.getString("id")));
                        newUserEntered = false;
                        enterNewUsername = null;
                        grabNames = true;
                        Log.d("Quentin", "FALSEGET17");
                        getData(2);
                    }
                    // Move to the next item.
                    i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Quentins Log", "username Crash");
        }
        userNameID = ProjectUsername.getUsernameID();
        usernameTextView.setText("Welcome Back " + ProjectUsername.getUsername(userNameID));
        if (grabNames) {
            Log.d("BadTime", "parseProjects: run2");
            Log.d("Quentin", "FALSEGET18");
            getData(1);
            grabNames = false;
        }
        if (newUserEntered || checkpass) {
            Intent intent = new Intent(this, signInPage.class);
            startActivityForResult(intent, 5);
            Log.d("Quentins Log", "Didnt pass");
            checkpass = false;
            newUserEntered = false;
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    public void getData(int queryInt) {
        if (queryInt <= 3 && queryInt >= 0) {

        /*
        0 returns everything
        1 returns times
        2 returns projects
        3 returns usernames
         */
            String queryString = Integer.toString(queryInt);

            loadID++;

            if (getSupportLoaderManager().getLoader(loadID) != null) {
                getSupportLoaderManager().initLoader(loadID, null, this);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                queryBundle.putString("method", "getData");
                getSupportLoaderManager().restartLoader(loadID, queryBundle, this);
            } else {
                if (queryString.length() == 0) {
                    displayToast("Please enter a search term");
                } else {
                    displayToast("Please check your network connection and try again.");
                }
            }
        } else {
            displayToast("Invalid get query number");
        }
//        displayToast("Ran get data");
    }

    public void postData(int queryInt, Bundle data) {
        if (queryInt <= 3 && queryInt >= 1) {

        /*
        1 post to times
        2 post to projects
        3 post to usernames
         */
            String queryString = Integer.toString(queryInt);

            loadID++;

            if (getSupportLoaderManager().getLoader(loadID) != null) {
                getSupportLoaderManager().initLoader(loadID, null, this);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                queryBundle.putString("method", "postData");
                queryBundle.putBundle("bundleOfData", data);
                getSupportLoaderManager().restartLoader(loadID, queryBundle, this);
            } else {
                if (queryString.length() == 0) {
                    displayToast("Please enter a search term");
                } else {
                    displayToast("Please check your network connection and try again.");
                }
            }
        } else {
            displayToast("Invalid get query number");
        }
//        displayToast("Ran post data");
    }

    public void putData(int queryInt, Bundle data) {
        if (queryInt <= 3 && queryInt >= 1) {

        /*
        1 post to times
        2 post to projects
        3 post to usernames
         */
            String queryString = Integer.toString(queryInt);

            loadID++;

            if (getSupportLoaderManager().getLoader(loadID) != null) {
                getSupportLoaderManager().initLoader(loadID, null, this);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                queryBundle.putString("method", "putData");
                queryBundle.putBundle("bundleOfData", data);
                getSupportLoaderManager().restartLoader(loadID, queryBundle, this);
            } else {
                if (queryString.length() == 0) {
                    displayToast("Please enter a search term");
                } else {
                    displayToast("Please check your network connection and try again.");
                }
            }
        } else {
            displayToast("Invalid get query number");
        }
//        displayToast("Ran post data");
    }

    public void deleteData(int queryInt, Bundle data) {
        if (queryInt <= 3 && queryInt >= 1) {

        /*
        1 post to times
        2 post to projects
        3 post to usernames
         */
            String queryString = Integer.toString(queryInt);

            loadID++;

            if (getSupportLoaderManager().getLoader(loadID) != null) {
                getSupportLoaderManager().initLoader(loadID, null, this);
            }

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                queryBundle.putString("method", "deleteData");
                queryBundle.putBundle("bundleOfData", data);
                getSupportLoaderManager().restartLoader(loadID, queryBundle, this);
            } else {
                if (queryString.length() == 0) {
                    displayToast("Please enter a search term");
                } else {
                    displayToast("Please check your network connection and try again.");
                }
            }
        } else {
            displayToast("Invalid get query number");
        }
//        displayToast("Ran delete data");
    }
}

