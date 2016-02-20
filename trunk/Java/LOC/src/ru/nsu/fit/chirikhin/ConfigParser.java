package ru.nsu.fit.chirikhin;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

/**
 * Created by cas on 15.02.16.
 */
public class ConfigParser implements Parser {

    HashMap<FilterIdentificator, String[]> filtersProperties;

    private static final String extensionFilterIdentificator = "Ext";

    ConfigParser(String filePath) throws IOException {

        File myFile = new File(filePath);
        Scanner myScanner = new Scanner(myFile);

        LinkedList<String> extensionFilterIdentificatorParams = new LinkedList<>();

        while(myScanner.hasNext()) {
            String filterIdentificator = myScanner.next();

            switch(filterIdentificator) {

                case extensionFilterIdentificator :

                    String filterParam;

                    try {
                        filterParam = myScanner.next();
                    }
                    catch(NoSuchElementException e) {
                        throw new NoSuchElementException("Invalid filter parameters!");
                    }
                    catch(IllegalStateException e) {
                        throw new IllegalStateException("Can't read next token. The scanner is closed!");
                    }

                    extensionFilterIdentificatorParams.add(filterParam);
                    break;

                default :

                    throw new IllegalArgumentException("Filter with such identificator doesn't exist!");
            }
        }

        filtersProperties.put(FilterIdentificator.fileExtensionFilter, extensionFilterIdentificatorParams.toArray(new String[extensionFilterIdentificatorParams.size()]));
    }

    public HashMap<FilterIdentificator, String[]> getFiltersMap() {
        return filtersProperties;
    }
}
