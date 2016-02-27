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
        PrintWriter writer = new PrintWriter("./config.txt", "UTF-8");
        writer.println("Ext txt");
        writer.println("Ext my l l");
        writer.close();
        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyString() throws IOException {
        PrintWriter writer = new PrintWriter("./config.txt", "UTF-8");
        writer.println("");
        writer.println("");
        writer.close();
        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test (expected = IllegalArgumentException.class)
    public void extFilterWithoutParam() throws IOException {
        PrintWriter writer = new PrintWriter("./config.txt", "UTF-8");
        writer.println("Ext");
        writer.close();
        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test (expected = IllegalArgumentException.class)
    public void oneFilterTwice() throws IOException {
        PrintWriter writer = new PrintWriter("./config.txt", "UTF-8");
        writer.println("Ext txt");
        writer.println("Ext txt");
        writer.close();
        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test (expected = IllegalArgumentException.class)
    public void usingNotExistedFilter() throws IOException {
        PrintWriter writer = new PrintWriter("./config.txt", "UTF-8");
        writer.println("Ext txt");
        writer.println("Ex txt");
        writer.close();
        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }

    @Test
    public void emptyFile() throws IOException {
        PrintWriter writer = new PrintWriter("./config.txt", "UTF-8");
        writer.close();
        ConfigParser parser = new ConfigParser(System.getProperty("user.dir") + "/config.txt");
    }
}
