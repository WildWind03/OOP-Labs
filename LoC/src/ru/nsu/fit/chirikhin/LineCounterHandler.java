package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.IOException;

public class LineCounterHandler implements BaseFileHandler {
    final private BaseFilter[] filters;
    private Statistics statistics;

    public LineCounterHandler(BaseFilter[] filters) {
        if (null == filters) {
            throw new NullPointerException("LineCounterHandler: can't create myself because argument is null");
        }

        this.filters = filters;
        statistics = new Statistics();
    }

    public void handle(File file) throws IOException{
        if (null == file) {
            throw new NullPointerException("LineCounterHandler: can't handle null reference");
        }
        for(BaseFilter filter : filters) {
            if (filter.isAppropriate(file)) {
                int countOfLines = LinesCounter.getNumberOfLines(file);
                statistics.updateStatistics(filter, countOfLines);
            }
        }
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
