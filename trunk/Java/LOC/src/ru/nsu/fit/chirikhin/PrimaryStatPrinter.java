package ru.nsu.fit.chirikhin;

import java.util.Arrays;

/**
 * Created by cas on 25.02.16.
 */
public class PrimaryStatPrinter implements StatPrinter {

    public PrimaryStatPrinter() {

    }

    public void printStatistics(Statistics[] stat) {
        Arrays.sort(stat);
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
