package edu.calvin.cs262.teama.timetracker;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVImportExport {
    public final String VALUE_DELIMITER = ",";
    private boolean isStarted;
    private Context context;

    public final static String[] CSV_HEADERS = {"Project", "User Name", "Timestamp", "Action", "Synced"};

    public CSVImportExport(Context applicationContext) throws IOException {
        this.context = applicationContext;
        this.isStarted = this.getCSVFile().exists();
        if(!this.isStarted) {
            this.createNewCSV();
        }
    }

    /**
     *
     * @param w a Writer object for writing to
     * @param values a list of values to write
     * @throws IOException
     */
    public void writeLine(Writer w, List<String> values) throws IOException {
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

    public String[][] importCSV(InputStream is) {
        Scanner scanner = new Scanner(is);
        ArrayList<String[]> lines = new ArrayList<String[]>();
        while(scanner.hasNextLine()) {
            String read_line = scanner.nextLine();
            if(!read_line.equals("")) {
                String[] line = read_line.split(",");
                lines.add(line);
            }
        }
        scanner.close();
        if (lines.size()  == 0)
            return null;
        String[][] rtn = new String[lines.size()][lines.get(0).length];
        rtn = lines.toArray(rtn);
        return rtn;
    }

    public void createNewCSV() throws IOException {
        if(getCSVFile().exists())
            throw new IOException("CSV File already exists!");
        if(!isExternalStorageReadable() || !isExternalStorageWritable())
            throw new IOException("Do not have permission to write to external storage (CSV)!");
        PrintWriter writer = new PrintWriter(getCSVFile().getAbsolutePath(), "UTF-8");
    }

    public File getApplicationDirectoryPath() {
        return context.getFilesDir();
    }

    public File getCSVFile() {
        return new File(getApplicationDirectoryPath(), "timedata.csv");
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
