package ru.nsu.fit.chirikhin;

import java.util.Arrays;

/**
 * to print Statistics as Developer wants
 */

public class PrimaryStatPrinter implements StatPrinter {

    public PrimaryStatPrinter() {

    }

    /**
     * @param stat An array of Statistics you want to print
     * @throws RuntimeException A system error which doesn't depend on the input
     */
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
