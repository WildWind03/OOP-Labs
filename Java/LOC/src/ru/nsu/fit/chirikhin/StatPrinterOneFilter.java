package ru.nsu.fit.chirikhin;

import java.util.Arrays;

/**
 * to print Statistics as teacher wants
 */

public class StatPrinterOneFilter implements StatPrinter {

    public StatPrinterOneFilter() {

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

        int maxExtensionStrLength = getMaxLengthOfExtensionString(stat);

        int counter = 0;

        for (int i = stat.length - 1; i >=0; i--) {
            if (0 != stat[i].getNumOfLines()) {
                counter++;

                if (2 == counter) {
                    System.out.println("------------");
                }

                printStatistics(stat[i], maxExtensionStrLength);
            }
        }
    }

    /**
     * @param stat A statistics you want to print
     * @param width It's a count of characters that definitely will be printed to align all the extension fields
     */

    public void printStatistics(Statistics stat, int width) {
        String formatString = "%-" + width + "s - %d lines in %d files\n";
        System.out.printf(formatString, stat.getDescription(), stat.getNumOfLines(), stat.getNumOfFiles());
    }

    /**
     * @param stat Array of Statistics
     * @return Maximum of descriptions lengths of each statistic from the array
     */

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
