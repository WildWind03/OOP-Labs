package ru.nsu.fit.chirikhin;

/**
 * Created by cas on 15.02.16.
 */

import java.lang.IllegalArgumentException;

public class FilterFactory {

    static public BaseFilter createFilters(FilterIdentificator filterName, String[] filterParams) throws IllegalArgumentException {

        BaseFilter filter;

        switch(filterName) {
            case fileExtensionFilter :

                filter = new FileExtensionFilter(filterParams);
                break;

            default :

                throw new IllegalArgumentException("Invalid filter name!");
        }

        return filter;
    }
}
