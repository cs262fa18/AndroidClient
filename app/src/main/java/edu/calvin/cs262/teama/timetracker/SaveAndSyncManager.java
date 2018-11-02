package edu.calvin.cs262.teama.timetracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Time;
import java.util.ArrayList;

public class SaveAndSyncManager implements Runnable {

    @Override
    public void run() {
        this.save();
        this.sync();
    }

    private void save() {
        // Save TimeEntry objects to a CSV file.
        File csvFile = MainActivity.csv.getCSVFile();
        try {
            csvFile.delete();
            Writer writer = MainActivity.csv.createNewCSV();

            for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
                ArrayList<String> csv_values = new ArrayList<String>();
                csv_values.add(te.getProject());
                csv_values.add(te.getUsername());
                csv_values.add(te.getTime().toString());
                csv_values.add(new Integer(te.getAction()).toString());
                csv_values.add(new Boolean(te.isSynced()).toString());

                MainActivity.csv.writeLine(writer, csv_values);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sync() {
        // Sync with database. To be implemented.
        //TODO: Implement
    }
}
