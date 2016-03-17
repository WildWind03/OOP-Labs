package ru.nsu.fit.chirikhin;

import java.util.List;
import java.util.Map;

/**
 * to print Statistics as teacher wants
 */

public class StatPrinterOneFilter implements StatPrinter {

    public StatPrinterOneFilter() {

    }

    public void printStatistics(Statistics stat) {
        List<Map.Entry<BaseFilter, Statistics.StatisticsInfo>> sortedStatistics = stat.getSortedStatisticsList();

        int maxExtensionStrLength = getMaxLengthOfExtensionString(sortedStatistics);

        int counter = 0;

        for (Map.Entry<BaseFilter, Statistics.StatisticsInfo> cur : sortedStatistics) {
            if (0 != cur.getValue().getNumOfLines()) {
                counter++;
            }

            if (2 == counter) {
                System.out.println("------------");
            }

            String formatString = "%-" + maxExtensionStrLength + "s - %d lines in %d files\n";
            System.out.printf(formatString, cur.getKey().getDescriptionForOutput(), cur.getValue().getNumOfLines(), cur.getValue().getNumOfFiles());
        }
    }

    private int getMaxLengthOfExtensionString(List<Map.Entry<BaseFilter, Statistics.StatisticsInfo>> statList) {
        if (0 == statList.size()) {
            throw new NullPointerException("StatPrinterOneFilter: Error! Trying to get max of length extension from empty list of Statistics!");
        }

        int max = 0;

        for (Map.Entry<BaseFilter, Statistics.StatisticsInfo> cur : statList) {
            if (cur.getKey().getDescriptionForOutput().length() > max && cur.getValue().getNumOfLines() != 0) {
                max = cur.getKey().getDescriptionForOutput().length();
            }
        }

        return max;
    }
}
