package ru.nsu.fit.chirikhin;

import java.util.Arrays;

public class StatPrinterOneFilter implements StatPrinter {

    public StatPrinterOneFilter() {

    }

    @Override
    public void printStatistics(Statistics[] stat) throws RuntimeException {
        try {
            Arrays.sort(stat);
        }
        catch(Exception e) {
            throw new RuntimeException("System Error! Can't sort statistics!", e);
        }

        int maxExtensionStrLength = getMaxLengthOfExtensionString(stat);

        for (int i = stat.length - 1; i >=0; i--) {
            if (0 != stat[i].getNumOfLines()) {
                printStatistics(stat[i], maxExtensionStrLength);
            }
            if (i == stat.length - 1) {
                System.out.println("------------");
            }
        }
    }

    public void printStatistics(Statistics stat, int width) {
        String formatString = "%-" + width + "s - %d lines in %d files\n";
        System.out.printf(formatString, stat.getDescription(), stat.getNumOfLines(), stat.getNumOfFiles());
    }

    private int getMaxLengthOfExtensionString(Statistics[] stat) {
        if (0 == stat.length) {
            throw new IllegalArgumentException("Error! Trying to get max of length extension from empty array of Statistics!");
        }

        int max = stat[0].getDescription().length();

        for (Statistics cur : stat) {
            if (cur.getDescription().length() > max) {
                max = cur.getDescription().length();
            }
        }

        return max;
    }
}
