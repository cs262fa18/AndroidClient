package edu.calvin.cs262.teama.timetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class TimeEntry {
    public static final int START_TIME = 0;
    public static final int END_TIME = 1;

    private static ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
    private boolean hasBeenDestroyed;
    private String project;
    private String username;
    private Date time;
    private int action;
    private boolean isSynced;


    public static ArrayList<TimeEntry> getAllTimeEntries() {
        return TimeEntry.timeEntries;
    }

    public TimeEntry(String project, String username, Date time, int action, boolean synced) {
        this.hasBeenDestroyed = false;
        timeEntries.add(this);

        this.project = project;
        this.username = username;
        this.time = time;
        this.action = action;
        this.isSynced = synced;
    }

    public void destroy() {
        timeEntries.remove(this);
        this.hasBeenDestroyed = true;
    }

    public static void readTimeEntriesFromFile(File f) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(f);
    }

    public static int getActionValueFromString(String s) {
        if (s.equals("START")) {
            return  TimeEntry.START_TIME;
        } else if (s.equals("STOP")) {
            return TimeEntry.END_TIME;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getAction() {
        return action;
    }

    public String getProject() {
        return project;
    }

    public String getUsername() {
        return username;
    }

    public Date getTime() {
        return time;
    }

    public boolean isSynced() {
        return isSynced;
    }
}
