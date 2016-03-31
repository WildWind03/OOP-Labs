package ru.nsu.ccfit.chirikhin.factorytests;

import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.ConfigParser;
import ru.nsu.ccfit.chirikhin.factory.DeveloperBugException;
import ru.nsu.ccfit.chirikhin.factory.InvalidConfigException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static junit.framework.TestCase.assertEquals;

public class ConfigParserTest {

    public ConfigParserTest() {

    }

    @Test (expected = NullPointerException.class)
    public void constructorTestNullReference() throws InvalidConfigException, IOException, DeveloperBugException {
        ConfigParser configParser = new ConfigParser(null);
    }

    @Test (expected = FileNotFoundException.class)
    public void constructorTestFileDoesNotExist() throws InvalidConfigException, IOException, DeveloperBugException {
        File file = new File("new");
        if (file.exists()) {
            throw new IOException("ConfigParserTest, constructorTestFileDoesNotExist: there is 'new' file! Can't check by this test");
        }

        ConfigParser configParser = new ConfigParser(file.getAbsolutePath());
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorTestFileIsDirectory() throws InvalidConfigException, IOException, DeveloperBugException {
        ConfigParser configParser = new ConfigParser("./");
    }

    @Test (expected = InvalidConfigException.class)
    public void constructorTestInvalidConfigLittleStrings () throws IOException, InvalidConfigException, DeveloperBugException {
        File file = new File ("./config.txt");

        if (file.exists()) {
            file.delete();
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println("CarBodyStorageSize=5");
            printWriter.println("EngineStorageSize=10");
        }

        ConfigParser configParser = new ConfigParser(file.getAbsolutePath());

    }

    @Test
    public void constructorTestValidConfig () throws IOException, InvalidConfigException, DeveloperBugException {
        File file = new File ("./config.txt");

        if (file.exists()) {
            file.delete();
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println("CarBodyStorageSize=5");
            printWriter.println("EngineStorageSize=10");
            printWriter.println("AccessoryStorageSize=6");
            printWriter.println("CarStorageSize=7");
            printWriter.println("CountOfAccessorySuppliers=8");
            printWriter.println("CountOfWorkers=9");
            printWriter.println("CountOfDealers=11");
            printWriter.println("Logging=1");
        }

        ConfigParser configParser = new ConfigParser(file.getAbsolutePath());

        assertEquals(5, configParser.getCarBodyStorageSize());
        assertEquals(10, configParser.getEngineStorageSize());
        assertEquals(6, configParser.getAccessoryStorageSize());
        assertEquals(7, configParser.getCarStorageSize());
        assertEquals(8, configParser.getAccessorySupplCount());
        assertEquals(9, configParser.getWorkersCount());
        assertEquals(11, configParser.getDealersCount());
        assertEquals(true, configParser.isLog());
    }

    @Test (expected = InvalidConfigException.class)
    public void constructorTestInvalidConfigIncorrectLines () throws IOException, InvalidConfigException, DeveloperBugException {
        File file = new File ("./config.txt");

        if (file.exists()) {
            file.delete();
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println("CarBodyStorageSize=5");
            printWriter.println("EngineStorageSize=10");
            printWriter.println("AccessoryStorageSize=6");
            printWriter.println("CarStorageSize=7");
            printWriter.println("CountOfAccessorySuppliers=8");
            printWriter.println("CountOfWorkers=9");
            printWriter.println("CountOfDealers=11");
            printWriter.println("Logging =1");
        }

        ConfigParser configParser = new ConfigParser(file.getAbsolutePath());

        assertEquals(5, configParser.getCarBodyStorageSize());
        assertEquals(10, configParser.getEngineStorageSize());
        assertEquals(6, configParser.getAccessoryStorageSize());
        assertEquals(7, configParser.getCarStorageSize());
        assertEquals(8, configParser.getAccessorySupplCount());
        assertEquals(9, configParser.getWorkersCount());
        assertEquals(11, configParser.getDealersCount());
        assertEquals(true, configParser.isLog());
    }

    @Test (expected = InvalidConfigException.class)
    public void constructorTestInvalidConfigIncorrectValues () throws IOException, InvalidConfigException, DeveloperBugException {
        File file = new File ("./config.txt");

        if (file.exists()) {
            file.delete();
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println("CarBodyStorageSize=5");
            printWriter.println("EngineStorageSize=10");
            printWriter.println("AccessoryStorageSize=6");
            printWriter.println("CarStorageSize=7");
            printWriter.println("CountOfAccessorySuppliers=8");
            printWriter.println("CountOfWorkers=9");
            printWriter.println("CountOfDealers=11");
            printWriter.println("Logging=1");
        }

        ConfigParser configParser = new ConfigParser(file.getAbsolutePath());

        assertEquals(5, configParser.getCarBodyStorageSize());
        assertEquals(10, configParser.getEngineStorageSize());
        assertEquals(6, configParser.getAccessoryStorageSize());
        assertEquals(7, configParser.getCarStorageSize());
        assertEquals(8, configParser.getAccessorySupplCount());
        assertEquals(9, configParser.getWorkersCount());
        assertEquals(11, configParser.getDealersCount());
        assertEquals(true, configParser.isLog());
    }

}
