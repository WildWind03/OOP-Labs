package ru.nsu.fit.chirikhin;

import java.io.IOException;
import java.util.*;
import java.io.File;

public class ConfigParserOneFilter implements Parser {

    private final Vector<FilterProperties> filters;

    ConfigParserOneFilter(String filePath) throws IOException, IllegalFormatException {

        filters = new Vector<>();

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
                        filters.addElement(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[]{ext}));
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
    public Vector<FilterProperties> getFiltersProperties() {
        return filters;
    }
}