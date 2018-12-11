package edu.calvin.cs262.teama.timetracker;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVImportExport {
    public final static String[] CSV_TIMES_HEADERS = {"UUID", "Project", "User Name", "Time start", "Time end", "Synced"};
    public final static String[] CSV_PROJECT_HEADER = {"ProjectName", "ProjectId", "ManagerId"};
    public final static String[] CSV_USERNAME_HEADER = {"USERNAME", "USERNAMEID"};
    public final String VALUE_DELIMITER = ",";
    private boolean timesIsStarted;
    private boolean projectsIsStarted;
    private Context context;

    public CSVImportExport(Context applicationContext) throws IOException {
        this.context = applicationContext;
        this.timesIsStarted = this.getTimesCSVFile().exists();
        if (!this.timesIsStarted) {
            this.createNewTimesCSV();
        }
        this.projectsIsStarted = this.getProjectsCSVFile().exists();
        if (!this.projectsIsStarted) {
            this.createNewProjectsCSV();
        }
    }

    /**
     * @param w      a Writer object for writing to
     * @param values a list of values to write
     * @throws IOException
     */
    public void writeTimesLine(Writer w, List<String> values) throws IOException {
        for (int i = 0; i < values.size(); i++) {
            // Add delimiter if this is not the first iteration
            if (i != 0) {
                w.write(VALUE_DELIMITER);
            }
            w.write(values.get(i));
        }

        // End the line with a newline character, and flush
        w.write('\n');
        w.flush();
    }

    public void writeProjectLine(Writer w, Object[] values) throws IOException {
        for (int i = 0; i < values.length; i++) {
            // Add delimiter if this is not the first iteration
            if (i != 0) {
                w.write(VALUE_DELIMITER);
            }
            w.write(values[i].toString());
        }

        // End the line with a newline character, and flush
        w.write('\n');
        w.flush();
    }

    public void writeUsernameLine(Writer w, String values) throws IOException {
        w.write(values);

        // End the line with a newline character, and flush
        w.write('\n');
        w.flush();
    }

    public String[][] importTimesCSV(InputStream is) {
        Scanner scanner = new Scanner(is);
        ArrayList<String[]> lines = new ArrayList<String[]>();
        while (scanner.hasNextLine()) {
            String read_line = scanner.nextLine();
            if (!read_line.equals("")) {
                String[] line = read_line.split(",");
                lines.add(line);
            }
        }
        scanner.close();
        if (lines.size() == 0)
            return null;
        String[][] rtn = new String[lines.size()][lines.get(0).length];
        rtn = lines.toArray(rtn);
        return rtn;
    }

    public String[][] importProjectsCSV(InputStream is) {
        Scanner scanner = new Scanner(is);
        ArrayList<String[]> lines = new ArrayList<String[]>();
        while (scanner.hasNextLine()) {
            String read_line = scanner.nextLine();
            if (!read_line.equals("")) {
                String[] line = read_line.split(",");
                lines.add(line);
            }
        }
        scanner.close();
        if (lines.size() == 0)
            return null;
        String[][] rtn = new String[lines.size()][lines.get(0).length];
        rtn = lines.toArray(rtn);
        return rtn;
    }

    public String[] importUsernameCSV(InputStream is) {
        Scanner scanner = new Scanner(is);
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String read_line = scanner.nextLine();
            if (!read_line.equals("")) {
                String line = read_line;
                lines.add(line);
            }
        }
        scanner.close();
        if (lines.size() == 0)
            return null;
        String[] rtn = new String[lines.size()];
        rtn = lines.toArray(rtn);
        return rtn;
    }

    public Writer createNewTimesCSV() throws IOException {
        if (getTimesCSVFile().exists())
            throw new IOException("CSV File already exists!");
        if (!isExternalStorageReadable() || !isExternalStorageWritable())
            throw new IOException("Do not have permission to write to external storage (CSV)!");
        PrintWriter writer = new PrintWriter(getTimesCSVFile().getAbsolutePath(), "UTF-8");
        writeTimesHeader(writer);
        return writer;
    }

    public Writer createNewProjectsCSV() throws IOException {
        if (getProjectsCSVFile().exists())
            throw new IOException("CSV File already exists!");
        if (!isExternalStorageReadable() || !isExternalStorageWritable())
            throw new IOException("Do not have permission to write to external storage (CSV)!");
        PrintWriter writer = new PrintWriter(getProjectsCSVFile().getAbsolutePath(), "UTF-8");
        writeProjectsHeader(writer);
        return writer;
    }

    public Writer createNewUsernameCSV() throws IOException {
        if (getUsernameCSVFile().exists())
            throw new IOException("CSV File already exists!");
        if (!isExternalStorageReadable() || !isExternalStorageWritable())
            throw new IOException("Do not have permission to write to external storage (CSV)!");
        PrintWriter writer = new PrintWriter(getUsernameCSVFile().getAbsolutePath(), "UTF-8");
        writeUsernameHeader(writer);
        return writer;
    }

    private void writeTimesHeader(Writer writer) throws IOException {
        ArrayList<String> header_values = new ArrayList<String>();
        for (String s : CSVImportExport.CSV_TIMES_HEADERS) {
            header_values.add(s);
        }
        writeTimesLine(writer, header_values);
    }

    private void writeProjectsHeader(Writer writer) throws IOException {
        ArrayList<String> header_values = new ArrayList<String>();
        for (String s : CSVImportExport.CSV_PROJECT_HEADER) {
            header_values.add(s);
        }
        writeTimesLine(writer, header_values);
    }

    private void writeUsernameHeader(Writer writer) throws IOException {
        ArrayList<String> header_values = new ArrayList<String>();
        for (String s : CSVImportExport.CSV_USERNAME_HEADER) {
            header_values.add(s);
        }
        writeTimesLine(writer, header_values);
    }

    public File getApplicationDirectoryPath() {
        return context.getExternalFilesDir(null);
    }

    public File getTimesCSVFile() {
        return new File(getApplicationDirectoryPath(), "timedata.csv");
    }

    public File getProjectsCSVFile() {
        return new File(getApplicationDirectoryPath(), "projectdata.csv");
    }

    public File getUsernameCSVFile() {
        return new File(getApplicationDirectoryPath(), "usernamedata.csv");
    }

    /* The following are from https://developer.android.com/training/data-storage/files#java */

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
