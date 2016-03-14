package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *This class is used to get Statistics about how much files and how much lines files[] exists
 */
public class Counter {
    /**
     *
     * @param filterList Array of filters you want to apply
     * @param files Array of files which you want to filter
     * @return Array of Statistics (one statistics for one filter)
     * @throws FileNotFoundException if file is not found
     */
    public static Statistics[] getStatistics(BaseFilter filterList[], File files[]) throws FileNotFoundException {

        Statistics stat[] = new Statistics[filterList.length + 1];

        for (int i = 0; i < filterList.length; ++i) {
            stat[i] = new Statistics();
            stat[i].setDescription(filterList[i].getDescriptionForOutput());
        }
        stat[filterList.length] = new Statistics();
        stat[filterList.length].setDescription("Total");
        stat[filterList.length].setNumOfFiles(files.length);

        for (File file : files) {
            int countOfLines = LinesCounter.getNumberOfLines(file);
            stat[filterList.length].addNumOfLines (countOfLines);

            for (int k = 0; k < filterList.length; ++k) {
                if (filterList[k].isAppropriate(file)) {
                    stat[k].addNumOfLines (countOfLines);
                    stat[k].addNumOfFiles(1);
                }
            }
        }

        return stat;
    }
}
