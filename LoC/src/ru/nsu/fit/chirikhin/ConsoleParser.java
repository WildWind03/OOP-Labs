package ru.nsu.fit.chirikhin;

/**
 * It's used to parse program args
 */

public class ConsoleParser {

    private String pathToConfig;
    private String pathToDirectory;

    static final private String helpStr = "Welcome to LoC by Chirikhin Alexander \n" +
            "Example of using the program: LoC.out ./config.txt ./testDir";
    static final private String configExampleStr = "Example of config.txt: \nExt txt \n Ext jpeg";

    public ConsoleParser(String params[]) {
        if (0 == params.length) {
            System.out.println(helpStr);
            System.out.println(configExampleStr);
            System.exit(0);
        }

        if (params.length != 2) {
            throw new IllegalArgumentException("Incorrect input! Invalid count of arguments!");
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
