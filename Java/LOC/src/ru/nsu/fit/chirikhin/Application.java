package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by cas on 15.02.16.
 */
class Application {

    public static void main(String[] arg) throws IOException {

        ConsoleParser consoleParser = new ConsoleParser(arg);
        String pathToConfig = consoleParser.getPathToConfig();
        String pathToDirectory = consoleParser.getPathToDirectory();

        ConfigParser configParser = new ConfigParser(pathToConfig);
        Vector<FilterProperties> filterIdentifier = configParser.getFiltersProperties();

        Vector<BaseFilter> filters = new Vector<>();

        for (FilterProperties aFilterIdentifier : filterIdentifier) {
            filters.add(FilterFactory.createFilters(aFilterIdentifier.getFilterIdentifier(), aFilterIdentifier.getParams()));
        }

        LinkedList<File> files = FileManager.getFullListOfFilesInDirectory(pathToDirectory);

        Statistics[] stat = Counter.getStatistics(filters.toArray(new BaseFilter[filters.size()]), files.toArray(new File[files.size()]));
        PrimaryStatPrinter printer = new PrimaryStatPrinter();
        printer.printStatistics(stat);
    }
}
