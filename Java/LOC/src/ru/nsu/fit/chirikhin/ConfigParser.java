package ru.nsu.fit.chirikhin;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

/**
 * Created by cas on 15.02.16.
 */
public class ConfigParser implements Parser {

    private final HashMap<FilterIdentifier, String[]> filtersProperties;

    private static final String extensionFilterIdentifier = "Ext";

    ConfigParser(String filePath) throws IOException {

        filtersProperties = new HashMap<>();

        File myFile = new File(filePath);
        Scanner myScanner = new Scanner(myFile);

        while(myScanner.hasNext()) {
            String filterIdentifier = myScanner.next();

            switch(filterIdentifier) {
                case extensionFilterIdentifier :

                    String filterParam;

                    try {
                        filterParam = myScanner.next();
                    }
                    catch(NoSuchElementException e) {
                        throw new NoSuchElementException("Invalid extension filter parameters!");
                    }
                    catch(IllegalStateException e) {
                        throw new IllegalStateException("Can't read next token. The scanner is closed!");
                    }

                    if (!(filtersProperties.containsKey(FilterIdentifier.fileExtensionFilter) && filtersProperties.containsValue(new String[] {filterParam}))) {
                        filtersProperties.put(FilterIdentifier.fileExtensionFilter, new String[] {filterParam});
                    }
                    else {
                        throw new IllegalArgumentException("One filter was met twice!");
                    }

                    break;

                default :
                    throw new IllegalArgumentException("Filter with such identifier doesn't exist!");
            }
        }
    }
    @Override
   public HashMap<FilterIdentifier, String[]> getFiltersMap() {
        return filtersProperties;
    }
}
