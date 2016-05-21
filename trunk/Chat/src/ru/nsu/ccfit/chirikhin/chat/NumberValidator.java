package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class NumberValidator {
    private static final Logger logger = Logger.getLogger(NumberValidator.class.getName());
    private NumberValidator() {

    }

    public static boolean isNumber(String string) {
        if (null == string) {
            throw new NullPointerException("Null instead of string");
        }

        return string.matches("\\d+");
    }
}
