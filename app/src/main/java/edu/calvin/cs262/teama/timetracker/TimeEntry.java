package edu.calvin.cs262.teama.timetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TimeEntry {
    private static ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
    private boolean hasBeenDestroyed;

    public static ArrayList<TimeEntry> getAllTimeEntries() {
        return TimeEntry.timeEntries;
    }

    public TimeEntry() {
        this.hasBeenDestroyed = false;
        timeEntries.add(this);
    }

    public void destroy() {
        timeEntries.remove(this);
    }

    public void finalize() throws Throwable {
        if(!this.hasBeenDestroyed)
            this.destroy();
        super.finalize();
    }

    public static void readTimeEntriesFromFile(File f) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(f);
    }
}
