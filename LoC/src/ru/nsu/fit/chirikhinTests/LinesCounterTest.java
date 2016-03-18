package ru.nsu.fit.chirikhinTests;

import org.junit.Test;
import ru.nsu.fit.chirikhin.LinesCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertTrue;

public class LinesCounterTest {
    public LinesCounterTest() {

    }

    @Test
    public void countLinesTest() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter writer = new PrintWriter("test.txt", "UTF-8")) {
            writer.println("The first line");
            writer.println("The second line");
        }
        File file = new File("test.txt");

        assertTrue(2 == LinesCounter.getNumberOfLines(file));
    }
}
