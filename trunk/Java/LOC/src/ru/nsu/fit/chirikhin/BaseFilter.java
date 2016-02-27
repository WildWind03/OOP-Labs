package ru.nsu.fit.chirikhin;

import java.io.File;

public interface BaseFilter {
    boolean isAppropriate(File file);
    String getDescriptionForOutput();
}
