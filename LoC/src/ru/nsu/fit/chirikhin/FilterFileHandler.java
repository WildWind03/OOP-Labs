package ru.nsu.fit.chirikhin;

import java.io.File;

/**
 * Created by cas on 15.03.16.
 */
public class FilterFileHandler implements BaseFileHandler {
    private BaseFilter[] filters;

    public FilterFileHandler(BaseFilter[] filters) {
        this.filters = filters;
    }

    public void handle(File file) {

    }

    public Statistics getStatistics() {

    }
}
