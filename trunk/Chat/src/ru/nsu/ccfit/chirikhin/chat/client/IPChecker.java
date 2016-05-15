package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPChecker {
    private static final Logger logger = Logger.getLogger(IPChecker.class.getName());

    private static final String IP_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private final static String PORT_PATTERN = "\\d+";

    private final static Pattern ipPattern = Pattern.compile(IP_PATTERN);
    private final static Pattern portPattern = Pattern.compile(PORT_PATTERN);

    private IPChecker() {

    }

    public static boolean isIp(String ipString) {
        if (null == ipString) {
            throw new IllegalArgumentException("Null reference instead of IP string");
        }

        Matcher matcher = ipPattern.matcher(ipString);
        return matcher.matches();
    }

    public static boolean isPort(String port) {
        if (null == port) {
            throw new IllegalArgumentException("Null reference instead of port string");
        }

        return portPattern.matcher(port).matches();
    }
}
