package edu.calvin.cs262.teama.timetracker;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVImportExport {
    final String VALUE_DELIMITER = ",";
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
}
