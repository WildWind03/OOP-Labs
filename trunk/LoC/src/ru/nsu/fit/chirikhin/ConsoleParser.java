package ru.nsu.fit.chirikhin;

/**
 * It's used to parse program args
 */

public class ConsoleParser {

    private String pathToConfig;
    private String pathToDirectory;

    static final private String HELP_STR = "Welcome to LoC by Chirikhin Alexander\n" +
            "Example of using the program: LoC.out ./config.txt ./testDir";
    static final private String CONFIG_EXAMPLE_STR = "Example of config.txt: \ntxt \njpeg";

    public ConsoleParser(String params[]) {
        if (0 == params.length) {
            System.out.println(HELP_STR);
            System.out.println(CONFIG_EXAMPLE_STR);
            System.exit(0);
        }

        if (params.length != 2) {
            throw new IllegalArgumentException("Console Parse: Incorrect input! Invalid count of arguments!");
        }

        this.pathToConfig = params[0];
        this.pathToDirectory = params[1];
    }

    public String getPathToConfig() {
        return pathToConfig;
    }

    public String getPathToDirectory() {
        return pathToDirectory;
    }
}
