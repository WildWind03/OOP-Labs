package ru.nsu.fit.chirikhin;

import java.lang.IllegalArgumentException;

class FilterFactory {

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
