package ru.nsu.fit.chirikhin;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfigParserTest {

    public ConfigParserTest() {

    }

    @Test(expected = FileNotFoundException.class)
    public void fileNotExistTest() throws IOException {
        ConfigParser parser = new ConfigParser("wrong path");
        parser.getFiltersProperties();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCountOfParametersOfExtensionFilter() throws IOException {
        try(PrintWriter writer = new PrintWriter("./config.txt", "UTF-8")) {
            writer.println("Ext txt");
            writer.println("Ext my l l");
        }

        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyString() throws IOException {
        try(PrintWriter writer = new PrintWriter("./config.txt", "UTF-8")) {
            writer.println("");
            writer.println("");
        }

        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test (expected = IllegalArgumentException.class)
    public void extFilterWithoutParam() throws IOException {
        try (PrintWriter writer = new PrintWriter("./config.txt", "UTF-8")) {
            writer.println("Ext");
        }

        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test (expected = IllegalArgumentException.class)
    public void oneFilterTwice() throws IOException {
        try (PrintWriter writer = new PrintWriter("./config.txt", "UTF-8")) {
            writer.println("Ext txt");
            writer.println("Ext txt");
        }

        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test (expected = IllegalArgumentException.class)
    public void usingNotExistedFilter() throws IOException {
        try (PrintWriter writer = new PrintWriter("./config.txt", "UTF-8")) {
            writer.println("Ext txt");
            writer.println("Ex txt");
        }

        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test
    public void emptyFile() throws IOException {
        try (PrintWriter writer = new PrintWriter("./config.txt", "UTF-8")) {}

        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }
}
