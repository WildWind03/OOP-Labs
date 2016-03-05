package ru.nsu.fit.chirikhin;

import java.lang.IllegalArgumentException;

/**
 * It's used to create filters
 */

public class FilterFactory {

    /**
     * @param filterName A special identifier for filter
     * @param filterParams Array of arguments for filter (one String for one argument)
     * @return Created filter
     * @throws IllegalArgumentException if there is invalid filter name or parameters for certain filter
     */
    static public BaseFilter createFilters(FilterIdentifier filterName, String[] filterParams) throws IllegalArgumentException {

        BaseFilter filter;

        switch(filterName) {
            case fileExtensionFilter :

                if (1 == filterParams.length) {
                    filter = new FileExtensionFilter(filterParams[0]);
                }
                else {
                    throw new IllegalArgumentException("Invalid parameters for File Extension Filter!");
                }

                break;

            default :

                throw new IllegalArgumentException("Invalid filter name!");
        }

        return filter;
    }
}
