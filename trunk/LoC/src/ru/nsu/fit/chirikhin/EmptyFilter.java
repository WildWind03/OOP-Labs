package ru.nsu.fit.chirikhin;

import java.io.File;

/**
 * Created by cas on 17.03.16.
 */
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
