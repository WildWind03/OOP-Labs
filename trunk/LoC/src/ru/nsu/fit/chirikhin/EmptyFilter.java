package ru.nsu.fit.chirikhin;

import java.io.File;

public class EmptyFilter implements BaseFilter {
    final static String descriptionForOutput = "Total";
    public EmptyFilter() {

    }

    @Override
    public boolean isAppropriate(File file)  {
        return true;
    }

    @Override
    public String getDescriptionForOutput() {
        return descriptionForOutput;
    }
}
