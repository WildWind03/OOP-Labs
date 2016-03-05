package ru.nsu.fit.chirikhin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;

/**
 * This class is used to parse config file and create vector of filters
 * @author Chirikhin Alexander
 */
public class ConfigParser implements Parser {

    private final Vector<FilterProperties> filters;

    private static final String extensionFilterIdentifier = "Ext";


    /**
     * @param filePath A path to the file where filters which the class should create are described
     * @throws FileNotFoundException if the file is not found
     * @throws IllegalArgumentException if there are invalid count of arguments for some filters
     * @throws RuntimeException if the problem in class realization, not in input
     */
    public ConfigParser(String filePath) throws FileNotFoundException{

        filters = new Vector<>();

        File myFile = new File(filePath);
        try (Scanner myScanner = new Scanner(myFile)) {

            while (myScanner.hasNextLine()) {

                String line = myScanner.nextLine();
                try(Scanner strScanner = new Scanner(line)) {

                    String filterIdentifier;

                    if (strScanner.hasNext()) {
                        filterIdentifier = strScanner.next();
                    } else {
                        throw new IllegalArgumentException("Wrong format of input!");
                    }

                    switch (filterIdentifier) {
                        case extensionFilterIdentifier:

                            String filterParam;

                            try {
                                filterParam = strScanner.next();
                            } catch (NoSuchElementException e) {
                                throw new IllegalArgumentException("Invalid extension filter parameters!", e);
                            } catch (IllegalStateException e) {
                                throw new RuntimeException("System Error!!! Can't read next token. The scanner is closed!", e);
                            }

                            if (!filters.contains(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[]{filterParam}))) {
                                filters.addElement(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[]{filterParam}));
                            } else {
                                throw new IllegalArgumentException("One filter was met twice!");
                            }

                            if (strScanner.hasNext()) {
                                throw new IllegalArgumentException("Too many arguments for Extension File Filter!");
                            }

                            break;

                        default:
                            throw new IllegalArgumentException("Filter with such identifier doesn't exist!");
                    }
                }

            }
        }
    }

    /**
     * Returns the vector of created filters
     * @return the vector of created filters
     */

    @Override
   public Vector<FilterProperties> getFiltersProperties() {
        return filters;
    }
}
