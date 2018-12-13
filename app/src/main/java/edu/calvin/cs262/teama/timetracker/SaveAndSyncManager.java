package edu.calvin.cs262.teama.timetracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * This class is for saving data to a phone via csv
 * Able to store Times, Projects, and Usernames
 * Only stores usernames in the final product
 *
 * @author Thomas Woltjer
 * @edited Quentin Barnes - Created the save projects and usernames by augmenting code for times
 */

public class SaveAndSyncManager implements Runnable {

    @Override
    public void run() {
        this.save();
        this.sync();
    }

    private void save() {
        // Save TimeEntry objects to a CSV file.
        File csvTimesFile = MainActivity.csv.getTimesCSVFile();
        try {
            csvTimesFile.delete();
            Writer writer = MainActivity.csv.createNewTimesCSV();

            for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
                ArrayList<String> csv_values = new ArrayList<String>();
                csv_values.add(te.getUUID().toString());
                csv_values.add(te.getProject());
                csv_values.add(te.getUsername());
                csv_values.add(te.getStartTime().toString());
                if (te.getEndTime() == null) {
                    csv_values.add("");
                } else {
                    csv_values.add(te.getEndTime().toString());

                }
                csv_values.add(Boolean.valueOf(te.isSynced()).toString());
                MainActivity.csv.writeTimesLine(writer, csv_values);
                writer.flush();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        try {
            File csvProjectFile = MainActivity.csv.getProjectsCSVFile();
            try {
                csvProjectFile.delete();
                Writer writer = MainActivity.csv.createNewProjectsCSV();

                for (Object[] Proj : ProjectUsername.getActivitiesList()) {
                    MainActivity.csv.writeProjectLine(writer, Proj);
                    writer.flush();
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        try {
            File csvUsernameFile = MainActivity.csv.getUsernameCSVFile();
            try {
                csvUsernameFile.delete();
                Writer writer = MainActivity.csv.createNewUsernameCSV();
                String username = Integer.toString(ProjectUsername.getUsernameID());
                MainActivity.csv.writeUsernameLine(writer, username);
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }


    private void sync() {
        // Sync with database. To be implemented.
        //TODO: Implement
    }
}
