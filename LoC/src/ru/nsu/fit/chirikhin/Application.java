package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

class Application {

    public static void main(String[] arg) throws IOException {

        try {
            ConsoleParser consoleParser = new ConsoleParser(arg);
            String pathToConfig = consoleParser.getPathToConfig();
            String pathToDirectory = consoleParser.getPathToDirectory();

            ConfigParserOneFilter configParser = new ConfigParserOneFilter(pathToConfig);
            Vector<FilterProperties> filterIdentifier = configParser.getFiltersProperties();

            Vector<BaseFilter> filters = new Vector<>();

            for (FilterProperties aFilterIdentifier : filterIdentifier) {
                filters.add(FilterFactory.createFilters(aFilterIdentifier.getFilterIdentifier(), aFilterIdentifier.getParams()));
            }

            FilterFileHandler filterFileHandler = new FilterFileHandler((BaseFilter[]) filters.toArray());

            //LinkedList<File> files = FileManager.getFullListOfFilesInDirectory(pathToDirectory);
            FileManager.handleFilesInDirectory(pathToDirectory, filterFileHandler);

            Statistics stat = filterFileHandler.getStatistics();

            //Statistics[] stat = Counter.getStatistics(filters.toArray(new BaseFilter[filters.size()]), files.toArray(new File[files.size()]));
            StatPrinterOneFilter printer = new StatPrinterOneFilter();
            printer.printStatistics(stat);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
