package edu.calvin.cs262.teama.timetracker;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for class CSVImportExport. Note to self: When looking to add functionality, first
 * write tests, then write code that will pass the tests. NOT THE OTHER WAY AROUND
 * <p>
 * Created by Thomas Woltjer on October 24, 2018
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CSVExportTests {
    /**
     * Tests writing a single line, with only one value. The most basic test in all of CSV.
     *
     * @throws IOException Exception if the Writer has an IOException. Shouldn't happen because we
     *                     are using the StringWriter class.
     */
    @Test
    public void testCreateHeaderOneColumn() throws IOException {
        StringWriter test_writer = new StringWriter();
        ArrayList<String> column_headers = new ArrayList<String>();
        column_headers.add("Column 1");
        CSVImportExport csv = new CSVImportExport();
        csv.writeTimesLine(test_writer, column_headers);
        assertEquals("Column 1\n", test_writer.toString());
    }

    /**
     * Tests writing multiple values on a single line to a csv.
     *
     * @throws IOException Passed up from Writer.writeTimesLine(). Because we are using StringWriter,
     *                     this should never be thrown.
     */
    @Test
    public void testCreateHeaderManyColumn() throws IOException {
        StringWriter test_writer = new StringWriter();
        ArrayList<String> column_headers = new ArrayList<String>();
        column_headers.add("Column 1");
        column_headers.add("Column 2");
        column_headers.add("Column 3");
        column_headers.add("Column 2");

        CSVImportExport csv = new CSVImportExport();
        csv.writeTimesLine(test_writer, column_headers);
        assertEquals("Column 1,Column 2,Column 3,Column 2\n", test_writer.toString());
    }
}