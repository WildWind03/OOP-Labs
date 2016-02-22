package ru.nsu.fit.chirikhin;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by cas on 22.02.16.
 */
public class ConfigParserTest {

    public ConfigParserTest() {

    }

    @Test(expected = FileNotFoundException.class)
    public void fileNotExistTest() throws IOException {
        ConfigParser parser = new ConfigParser("wrong path");
    }

    @Test(expected = NoSuchElementException.class)
    public void invalidCountOfParametersOfExtensionFilter() throws IOException {

        ConfigParser parser = new ConfigParser("");
    }
}
