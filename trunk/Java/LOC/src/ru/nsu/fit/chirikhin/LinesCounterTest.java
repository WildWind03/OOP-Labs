package ru.nsu.fit.chirikhin;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertTrue;

/**
 * Created by cas on 25.02.16.
 */
public class LinesCounterTest {
    public LinesCounterTest() {

    }

    @Test
    public void countLinesTest() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("test.txt", "UTF-8");
        writer.println("The first line");
        writer.println("The second line");
        writer.close();

        File file = new File("test.txt");

        assertTrue(2 == LinesCounter.getNumberOfLines(file));
    }
}
