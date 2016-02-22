package ru.nsu.fit.chirikhin;

import java.io.File;

/**
 * Created by cas on 16.02.16.
 */
public interface BaseFilter {
    boolean isAppropriate(File file);
    String getDescriptionForOutput();
}
