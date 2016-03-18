package ru.nsu.fit.chirikhin;

import java.util.List;
import java.util.Map;

/**
 * to print Statistics as teacher wants
 */

public class StatPrinterOneFilter implements StatPrinter {

    final static int MIN_LENGTH_FOR_OUTPUT = 5;
    final static String TOTAL_STR = "Total";

    public StatPrinterOneFilter() {

    }

    public void printStatistics(Statistics stat) {
        if (null == stat) {
            throw new NullPointerException("StatPrinterOneFilter: Error! can't print null as Statistics!");
        }

        List<Map.Entry<BaseFilter, Statistics.StatisticsInfo>> sortedStatistics = stat.getSortedStatisticsList();

        int maxExtensionStrLength = getMaxLengthOfExtensionString(sortedStatistics);

        int countTotalOfFiles = 0;
        int countTotalOfLines = 0;

        for (Map.Entry<BaseFilter, Statistics.StatisticsInfo> cur : sortedStatistics) {
            countTotalOfFiles+=cur.getValue().getNumOfFiles();
            countTotalOfLines+=cur.getValue().getNumOfLines();
        }
        String formatString = "%-" + maxExtensionStrLength + "s - %d lines in %d files\n";
        System.out.printf(formatString, TOTAL_STR, countTotalOfLines, countTotalOfFiles);
        System.out.println("------------");

        for (Map.Entry<BaseFilter, Statistics.StatisticsInfo> cur : sortedStatistics) {
            System.out.printf(formatString, cur.getKey().getDescriptionForOutput(), cur.getValue().getNumOfLines(), cur.getValue().getNumOfFiles());
        }
    }

    private int getMaxLengthOfExtensionString(List<Map.Entry<BaseFilter, Statistics.StatisticsInfo>> statList) {
        if (0 == statList.size()) {
            return MIN_LENGTH_FOR_OUTPUT;
        }

        int max = 0;

        for (Map.Entry<BaseFilter, Statistics.StatisticsInfo> cur : statList) {
            if (cur.getKey().getDescriptionForOutput().length() > max && cur.getValue().getNumOfLines() != 0) {
                max = cur.getKey().getDescriptionForOutput().length();
            }
        }

        if (max < MIN_LENGTH_FOR_OUTPUT) {
            max = MIN_LENGTH_FOR_OUTPUT;
        }

        return max;
    }
}
