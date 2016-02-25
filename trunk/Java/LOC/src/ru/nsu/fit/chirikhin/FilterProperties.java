package ru.nsu.fit.chirikhin;

import java.util.Arrays;

/**
 * Created by cas on 23.02.16.
 */
public class FilterProperties {

    final private FilterIdentifier filterIdentifier;
    final private String[] params;

    public FilterProperties(FilterIdentifier filterIdentifier, String[] params) {
        this.filterIdentifier = filterIdentifier;
        this.params = params;
    }

    public FilterIdentifier getFilterIdentifier() {
        return filterIdentifier;
    }

    public String[] getParams() {
        return params;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (null == obj) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        FilterProperties filterProperties = (FilterProperties) obj;

        if ((filterIdentifier == filterProperties.getFilterIdentifier()) && Arrays.equals(params, filterProperties.getParams())) {
            return true;
        }
        return false;
    }
}