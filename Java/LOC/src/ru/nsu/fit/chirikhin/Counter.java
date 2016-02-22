package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by cas on 21.02.16.
 */
class Counter {
    public static Statistics[] getStatistics(BaseFilter filterList[], File files[]) throws FileNotFoundException {

        Statistics stat[] = new Statistics[filterList.length + 1];

        System.out.println(filterList.length);
        System.out.flush();

        for (int i = 0; i < filterList.length; ++i) {
            stat[i].description = filterList[i].getDescriptionForOutput();
        }

        stat[filterList.length].description = "NO FILTER: ";
        stat[filterList.length].numOfFiles = files.length;

        for (File file : files) {
            int countOfLines = LinesCounter.getNumberOfLines(file);
            stat[filterList.length].numOfLines += countOfLines;

            for (int k = 0; k < filterList.length; ++k) {
                if (filterList[k].isAppropriate(file)) {
                    stat[k].numOfLines += countOfLines;
                    stat[k].numOfFiles++;
                }
            }
        }

        return stat;
    }
}
