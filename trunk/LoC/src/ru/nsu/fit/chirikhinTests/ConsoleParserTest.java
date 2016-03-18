package ru.nsu.fit.chirikhinTests;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import ru.nsu.fit.chirikhin.ConsoleParser;

public class ConsoleParserTest {
    public ConsoleParserTest() {

    }

    @Test
    public void testGetPaths() {
        String[] str = new String[] {"Hi", "End"};
        ConsoleParser consoleParser = new ConsoleParser(str);
        assertTrue (consoleParser.getPathToConfig().equals("Hi"));
        assertTrue (consoleParser.getPathToDirectory().equals("End"));
    }
}
