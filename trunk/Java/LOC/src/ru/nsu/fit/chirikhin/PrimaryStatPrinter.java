package ru.nsu.fit.chirikhin;

import java.util.Arrays;

public class PrimaryStatPrinter implements StatPrinter {

    public PrimaryStatPrinter() {

    }

    @Override
    public void printStatistics(Statistics[] stat) throws RuntimeException {
        try {
            Arrays.sort(stat);
        }
        catch(Exception e) {
            throw new RuntimeException("System Error! Can't sort statistics!", e);
        }
        for (int i = stat.length - 1; i >=0; i--) {
            if (0 != stat[i].getNumOfLines()) {
                printStatistics(stat[i]);
            }
        }
    }

    public void printStatistics(Statistics stat) {
        System.out.println(stat.getDescription() + " - " + stat.getNumOfLines() + " lines in " + stat.getNumOfFiles() + " files");
    }
}
