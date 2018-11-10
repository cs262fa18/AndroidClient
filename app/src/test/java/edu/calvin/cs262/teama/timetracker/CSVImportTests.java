package edu.calvin.cs262.teama.timetracker;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Unit tests for class CSVImportExport. Note to self: When looking to add functionality, first
 * write tests, then write code that will pass the tests. NOT THE OTHER WAY AROUND
 *
 * Created by Thomas Woltjer on October 26, 2018
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CSVImportTests {
    /**
     * Tests reading a single line, with only one value. The most basic test in all of CSV importing.
     * @throws IOException if there is a problem closing our ByteArrayInputStream
     */
    @Test
    public void testReadOneValue() throws IOException {
        String value = "My value";
        String line1 = value + "\n";
        String lines = line1;
        InputStream is = new ByteArrayInputStream(lines.getBytes(StandardCharsets.UTF_8));

        CSVImportExport csv = new CSVImportExport();
        String[][] csvDocument = csv.importTimesCSV(is);
        is.close();
        assertNotNull(csvDocument);
        assertEquals(csvDocument.length, 1);
        assertEquals(csvDocument[0].length, 1);
        assertEquals(csvDocument[0][0], value);
    }

    /**
     * Tests reading a single line, with multiple values.
     * @throws IOException if there is a problem closing our ByteArrayInputStream
     */
    @Test
    public void testReadOneLine() throws IOException {
        String value1 = "My value";
        String value2 = "My value 2";
        String value3 = "value3";

        String line1 = value1 + "," + value2 + "," + value3 + "\n";
        String lines = line1;
        InputStream is = new ByteArrayInputStream(lines.getBytes(StandardCharsets.UTF_8));

        CSVImportExport csv = new CSVImportExport();
        String[][] csvDocument = csv.importTimesCSV(is);
        is.close();
        assertNotNull(csvDocument);
        assertEquals(csvDocument.length, 1);
        assertEquals(csvDocument[0].length, 3);
        assertEquals(csvDocument[0][0], value1);
        assertEquals(csvDocument[0][1], value2);
        assertEquals(csvDocument[0][2], value3);

    }

    /**
     * Tests reading a single line, with multiple values.
     * @throws IOException if there is a problem closing our ByteArrayInputStream
     */
    @Test
    public void testReadManyLines() throws IOException {
        String value1 = "My value";
        String value2 = "My value 2";
        String value3 = "value3";
        String value4 = "row2val1";
        String value5 = "row2val2";

        String line1 = value1 + "," + value2 + "," + value3 + "\n";
        String line2 = value4 + "," + value5 + "\n";

        String lines = line1 + line2;
        InputStream is = new ByteArrayInputStream(lines.getBytes(StandardCharsets.UTF_8));

        CSVImportExport csv = new CSVImportExport();
        String[][] csvDocument = csv.importTimesCSV(is);
        is.close();
        assertNotNull(csvDocument);
        assertEquals(csvDocument.length, 2);
        assertEquals(csvDocument[0].length, 3);
        assertEquals(csvDocument[0][0], value1);
        assertEquals(csvDocument[0][1], value2);
        assertEquals(csvDocument[0][2], value3);
        assertEquals(csvDocument[1][0], value4);
        assertEquals(csvDocument[1][1], value5);
    }

}