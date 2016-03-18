package ru.nsu.fit.chirikhin;

import java.io.IOException;
import java.util.ArrayList;

class Application {

    public static void main(String[] arg) throws IOException {

        try {
            ConsoleParser consoleParser = new ConsoleParser(arg);
            String pathToConfig = consoleParser.getPathToConfig();
            String pathToDirectory = consoleParser.getPathToDirectory();

            ConfigParserOneFilter configParser = new ConfigParserOneFilter(pathToConfig);
            ArrayList<FilterProperties> filterIdentifier = configParser.getFiltersProperties();

            ArrayList<BaseFilter> filters = new ArrayList<>();

            for (FilterProperties aFilterIdentifier : filterIdentifier) {
                filters.add(FilterFactory.createFilters(aFilterIdentifier.getFilterIdentifier(), aFilterIdentifier.getParams()));
            }

            LineCounterHandler filterFileHandler = new LineCounterHandler(filters.toArray(new BaseFilter[filters.size()]));

            FileManager manager = new FileManager();
            manager.handleFilesInDirectory(pathToDirectory, filterFileHandler);

            Statistics stat = filterFileHandler.getStatistics();
            StatPrinterOneFilter printer = new StatPrinterOneFilter();
            printer.printStatistics(stat);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
