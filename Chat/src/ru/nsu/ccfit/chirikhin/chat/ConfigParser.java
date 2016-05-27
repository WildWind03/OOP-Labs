package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigParser {
    private static final Logger logger = Logger.getLogger(ConfigParser.class.getName());
    private final static String LOGGING_STR = "LOGGING";
    private final boolean isLog;

    public ConfigParser(String pathToConfig) throws IOException, InvalidConfigException {
        if (null == pathToConfig) {
            logger.error("Null pointer instead of path");
            throw new NullPointerException("Null pointer instead of path");
        }

        File file =  new File(pathToConfig);

        if (!file.exists()) {
            logger.error(file.getAbsolutePath() + " doesn't exist");
            throw new FileNotFoundException("Config Parser: " + pathToConfig + " doesn't exist");
        }

        if (!file.isFile()) {
            logger.error(pathToConfig + " is not a file");
            throw new IllegalArgumentException("Config Parser: " + pathToConfig + " is not a file");
        }


        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            Properties properties = new Properties();
            properties.load(bufferedReader);
            String isLogStr = properties.getProperty(LOGGING_STR);

            if (null == isLogStr) {
                logger.error("There is no property with name " + LOGGING_STR + " in config");
                throw new InvalidConfigException("There is no property with name " + LOGGING_STR + " in config");
            }

            if (!NumberValidator.isNumber(isLogStr)) {
                logger.error("Log str is not a number");
                throw new InvalidConfigException("Invalid config. Value of log is not an integer");
            }

            int isLog = Integer.parseInt(isLogStr);
            switch (isLog) {
                case 0:
                    this.isLog = false;
                    logger.info("Logging is turned off");
                    break;

                case 1:
                    this.isLog = true;
                    logger.info("Logging is turned on");
                    break;

                default:
                    logger.error("Error! Value of log propery must be 0 or 1");
                    throw new InvalidConfigException("Invalid config. Value of log must be 0 or 1");
            }
        }
    }

     public boolean isLog() {
        return isLog;
     }
}
