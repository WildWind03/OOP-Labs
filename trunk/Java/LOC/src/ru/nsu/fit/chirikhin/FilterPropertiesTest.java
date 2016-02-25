package ru.nsu.fit.chirikhin;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by cas on 23.02.16.
 */
public class FilterPropertiesTest {
    public FilterPropertiesTest() {

    }

    @Test
    public void equalTest() {
        FilterProperties prop = new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[] {"yes", "no"});
        assertTrue(prop.equals(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[] {"yes", "no"})));
        assertFalse(prop.equals(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[] {"yes", "yes"})));
        assertFalse(prop.equals(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[] {"yes", "no", "hi"})));
        assertFalse(prop.equals(new FilterProperties(FilterIdentifier.fileExtensionFilter, new String[] {"yes"})));
    }
}
