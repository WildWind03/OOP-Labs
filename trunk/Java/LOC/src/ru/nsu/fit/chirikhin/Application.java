package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

/**
 * Created by cas on 15.02.16.
 */
class Application {

    public static void main(String[] arg) throws IOException {

        /*ConsoleParser consoleParser = new ConsoleParser(arg);
        String pathToConfig = consoleParser.getPathToConfig();
        String pathToDirectory = consoleParser.getPathToDirectory();

        ConfigParser configParser = new ConfigParser(pathToConfig);
        Vector<> filterIdentifierHashMap = configParser.getFiltersProperties();

        System.out.println(filterIdentifierHashMap.size());
        System.out.flush();

        Vector<BaseFilter> filters = new Vector<>();

        for (Map.Entry<FilterIdentifier, String[]> entry: filterIdentifierHashMap.entrySet()) {
            filters.add(FilterFactory.createFilters(entry.getKey(), entry.getValue()));
        }

        LinkedList<File> files = FileManager.getFullListOfFilesInDirectory(pathToDirectory);

        Statistics[] stat = Counter.getStatistics(filters.toArray(new BaseFilter[filters.size()]), files.toArray(new File[files.size()]));

        for (Statistics aStat : stat) {
            System.out.println(aStat.description + "Count Of Lines: " + aStat.numOfLines + " Count of Files: " + aStat.numOfFiles + "\n");
        }
        */
    }
}