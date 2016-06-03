package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;

import java.util.List;

public class ConsoleParser {
    private static final Logger logger = Logger.getLogger(ConsoleParser.class.getName());
    private final static int NORMAL_COUNT_OF_ARGS = 1;
    private final String pathToFile;

    public ConsoleParser(List<String> args) {
        if (null == args) {
            throw new NullPointerException("Null instead of args");
        }

        if (NORMAL_COUNT_OF_ARGS != args.size()) {
            throw new IllegalArgumentException("Invalid count of app args");
        }

        pathToFile = args.get(0);
    }

    public String getPathToFile() {
        return pathToFile;
    }
}
