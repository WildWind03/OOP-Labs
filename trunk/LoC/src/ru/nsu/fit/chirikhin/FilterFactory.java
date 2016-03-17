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
                    throw new IllegalArgumentException("Filter Factory: Invalid parameters for File Extension Filter!");
                }

                break;
            case emptyFilter :

                if (null != filterParams) {
                    throw new IllegalArgumentException("Filter Factory: Too may parameters for EmptyFilter!");
                }

                filter = new EmptyFilter();
                break;

            default :

                throw new IllegalArgumentException("Filter Factory: Invalid filter name!");
        }

        return filter;
    }
}
