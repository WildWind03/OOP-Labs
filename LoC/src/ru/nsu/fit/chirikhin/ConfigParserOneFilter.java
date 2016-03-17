package ru.nsu.fit.chirikhin;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

/**
 * This class is used to parse config file and create only FileExtensionFilters
 */
public class ConfigParserOneFilter implements Parser {

    private final ArrayList<FilterProperties> filters;

    /**
     * @param filePath A path to the file where filters which the class should create are described
     * @throws FileNotFoundException if the file is not found
     * @throws IllegalArgumentException if there are invalid count of arguments for some filters
     * @throws RuntimeException if the problem in class realization, not in input
     */
    public ConfigParserOneFilter(String filePath) throws FileNotFoundException {

        filters = new ArrayList<>();

        File myFile = new File(filePath);
        try (Scanner myScanner = new Scanner(myFile)) {

            while (myScanner.hasNextLine()) {

                String line = myScanner.nextLine();
                try(Scanner strScanner = new Scanner(line)) {

                    String ext;

                    if (strScanner.hasNext()) {
                        ext = strScanner.next();
                    } else {
                        throw new IllegalArgumentException("Wrong format of input!");
                    }


                    if (!filters.contains(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[]{ext}))) {
                        filters.add(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[]{ext}));
                    } else {
                        throw new IllegalArgumentException("One filter was met twice!");
                    }

                    if (strScanner.hasNext()) {
                        throw new IllegalArgumentException("Too many arguments for Extension File Filter!");
                    }
                }

            }
        }
    }

    @Override
    public ArrayList<FilterProperties> getFiltersProperties() {
        return filters;
    }
}