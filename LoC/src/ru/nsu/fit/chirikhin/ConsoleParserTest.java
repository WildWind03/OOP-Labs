package ru.nsu.fit.chirikhin;

import static junit.framework.TestCase.assertEquals;
import org.junit.*;

public class ConsoleParserTest {
    public ConsoleParserTest() {

    }

    @Test
    public void testGetPaths() {
        String[] str = new String[] {"Hi", "End"};
        ConsoleParser consoleParser = new ConsoleParser(str);
        assertEquals ("Hi", consoleParser.getPathToConfig());
        assertEquals ("End", consoleParser.getPathToDirectory());
    }
}
