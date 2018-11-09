package edu.calvin.cs262.teama.timetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.UUID;

public class TimeEntry {
    public static final int START_TIME = 0;
    public static final int END_TIME = 1;

    private static ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
    private boolean hasBeenDestroyed;
    private String project;
    private String username;
    private Date start_time;
    private Date end_time;
    private UUID uuid;
    private boolean isSynced;


    public static ArrayList<TimeEntry> getAllTimeEntries() {
        return TimeEntry.timeEntries;
    }

    public TimeEntry(UUID uuid, String project, String username, Date start_time, Date end_time, boolean synced) {
        setup(uuid, project, username, start_time, end_time, synced);
    }

    public TimeEntry(String project, String username, Date start_time, Date end_time, boolean synced) {
        boolean creating_uuid = true;
        UUID uuid = null;
        while (creating_uuid) {
            uuid = UUID.randomUUID();
            creating_uuid = false;
            for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
                if (te.getUUID().equals(uuid)) {
                    creating_uuid = true;
                    break;
                }
            }
        }
        setup(uuid, project, username, start_time, end_time, synced);
    }

    private void setup(UUID uuid, String project, String username, Date start_time, Date end_time, boolean synced) {
        this.hasBeenDestroyed = false;
        timeEntries.add(this);

        this.uuid = uuid;
        this.project = project;
        this.username = username;
        this.start_time = start_time;
        this.end_time = end_time;
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

    public String getProject() {
        return project;
    }

    public String getUsername() {
        return username;
    }

    public Date getStartTime() {
        return start_time;
    }

    public boolean isSynced() {
        return isSynced;
    }
    public UUID getUUID() {
        return this.uuid;
    }

    public Date getEndTime() {
        return end_time;
    }

    public void setEndTime(Date endTime) {
        this.end_time = endTime;
    }

    public static void clearTimeEntries() {
        try {
            for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
                te.destroy();
            }
        } catch (ConcurrentModificationException e) {
            // Try again, until it works
            clearTimeEntries();
        }
    }

    /**
     * Gets the total amount of time worked on a project in milliseconds
     * @param project_name the name of the project
     * @return cumulative milliseconds that people have worked on the designated project
     */
    public static long getProjectTime(String project_name) {
        for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
            long millis = 0;
            if (te.getProject().equals(project_name)) {
                Date start_time = te.getStartTime();
                Date end_time;
                if (te.getEndTime() == null) {
                    end_time = new Date();
                } else {
                    end_time = te.getEndTime();
                }
                millis += (end_time.getTime() - start_time.getTime());
            }
            return millis;
        }
    }
}
