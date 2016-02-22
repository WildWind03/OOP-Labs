package ru.nsu.fit.chirikhin;

import static junit.framework.TestCase.assertEquals;
import org.junit.*;

/**
 * Created by cas on 22.02.16.
 */
public class ConsoleParserTest {
    public ConsoleParserTest() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput() throws IllegalArgumentException {
        String str[] = new String[]{"End"};
        ConsoleParser consoleParser = new ConsoleParser(str);
    }

    @Test
    public void testGetPaths() {
        String[] str = new String[] {"Hi", "End"};
        ConsoleParser consoleParser = new ConsoleParser(str);
        assertEquals ("Hi", consoleParser.getPathToConfig());
        assertEquals ("End", consoleParser.getPathToDirectory());
    }

    @Test
    public void testHelp() {
        String[] str = new String[] {};
        ConsoleParser consoleParser = new ConsoleParser(str);
    }
}
