package ru.nsu.fit.chirikhin;

import java.util.HashMap;

/**
 * Created by cas on 15.02.16.
 */
interface Parser {
    HashMap<FilterIdentifier, String[]> getFiltersMap();
}
