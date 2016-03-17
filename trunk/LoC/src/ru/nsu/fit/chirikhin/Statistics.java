package ru.nsu.fit.chirikhin;

import java.util.*;

/**
 * to contain information about how much files and how much lines in these files were found by certain filter
 */

public class Statistics {

    static public class StatisticsInfo implements Comparable{
        private int numOfLines;
        private int numOfFiles;

        public StatisticsInfo(int numOfFiles, int numOfLines) {
            this.numOfFiles = numOfFiles;
            this.numOfLines = numOfLines;
        }

        public StatisticsInfo() {
            this.numOfFiles = 0;
            this.numOfLines = 0;
        }

        public int getNumOfFiles() {
            return numOfFiles;
        }

        public int getNumOfLines() {
            return numOfLines;
        }

        public void setNumOfLines(int numOfLines) {
            this.numOfLines = numOfLines;
        }

        public void setNumOfFiles(int numOfFiles) {
            this.numOfFiles = numOfFiles;
        }

        public void addNumOfLines(int numOfLines) {
            this.numOfLines += numOfLines;
        }

        public void addNumOfFiles(int numOfFiles) {
            this.numOfFiles += numOfFiles;
        }

        @Override
        public int compareTo(Object o) {
            if (o.getClass() != this.getClass()) {
                throw new ClassCastException("Error! Can't compare statistics info because of one of the objects is not statistic info!");
            }
            else {
                StatisticsInfo statInfo = (StatisticsInfo) o;
                if (this.getNumOfLines() < statInfo.getNumOfLines()) {
                    return -1;
                }
                else {
                    if (this.getNumOfLines() < statInfo.getNumOfLines()) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }


        }
    }

    private HashMap<BaseFilter, StatisticsInfo> filtersStatMap;

    public Statistics() {
        filtersStatMap = new HashMap<>();
    }

    public int getNumOfFiles(BaseFilter filter) {
        if (!filtersStatMap.containsKey(filter)) {
            throw new NullPointerException("Statistics: can't get num of files because of null reference!");
        }

        return filtersStatMap.get(filter).getNumOfFiles();
    }

    public int getNumOfLines(BaseFilter filter) {
        if (!filtersStatMap.containsKey(filter)) {
            throw new NullPointerException("Statistics: can't get num of lines because of null reference!");
        }

        return filtersStatMap.get(filter).getNumOfLines();
    }

    public void setNumOfLines(BaseFilter filter, int numOfLines) {
        if (!filtersStatMap.containsKey(filter)) {
            throw new NullPointerException("Statistics: can't set num of lines because of null reference!");
        }

        filtersStatMap.get(filter).setNumOfLines(numOfLines);
    }

    public void setNumOfFiles(BaseFilter filter, int numOfFiles) {
        if (filtersStatMap.containsKey(filter)) {
            filtersStatMap.get(filter).setNumOfFiles(numOfFiles);
        }
        else {
            throw new NullPointerException("Statistics: can't set num of files because of null reference!");
        }
    }

    public void addNumOfLines(BaseFilter filter, int numOfLines) {
        if (!filtersStatMap.containsKey(filter)) {
            throw new NullPointerException("Statistics: can't add num of lines because of null reference!");
        }

        filtersStatMap.get(filter).addNumOfLines(numOfLines);
    }

    public void addNumOfFiles(BaseFilter filter, int numOfFiles) {
        if (!filtersStatMap.containsKey(filter)) {
            throw new NullPointerException("Statistics: can't add num of files because of null reference!");
        }

        filtersStatMap.get(filter).addNumOfFiles(numOfFiles);
    }

    /**
     * to add new information to Statistics
     */
    public void updateStatistics (BaseFilter filter, int numOfLines, int numOfFiles) {
        if (filtersStatMap.containsKey(filter)) {
            filtersStatMap.get(filter).addNumOfLines(numOfLines);
            filtersStatMap.get(filter).addNumOfFiles(numOfFiles);
        }
        else {
            filtersStatMap.put(filter, new StatisticsInfo(numOfLines, numOfFiles));
        }
    }

    /**
     * to create a sortet List of Statistics
     */

    public List<Map.Entry<BaseFilter, StatisticsInfo>>  getSortedStatisticsList() {
        List<Map.Entry<BaseFilter, StatisticsInfo>> list = new LinkedList<>(filtersStatMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<BaseFilter, StatisticsInfo>>() {
            @Override
            public int compare(Map.Entry<BaseFilter, StatisticsInfo> baseFilterStatisticsInfoEntry, Map.Entry<BaseFilter, StatisticsInfo> t1) {
                return baseFilterStatisticsInfoEntry.getValue().compareTo(t1.getValue());
            }
        });

        Collections.reverse(list);

        return list;
    }
}
