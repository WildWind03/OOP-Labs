package ru.nsu.fit.chirikhinTests;

import org.junit.Test;
import ru.nsu.fit.chirikhin.FilterFactory;
import ru.nsu.fit.chirikhin.FilterIdentifier;

public class FilterFactoryTest {
    public FilterFactoryTest() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void createFileExtFilterNoArgs() {
        FilterFactory.createFilters(FilterIdentifier.fileExtensionFilter, new String[] {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFileExtFilterManyArgs() {
        FilterFactory.createFilters(FilterIdentifier.fileExtensionFilter, new String[] {"me", "you"});
    }
}
