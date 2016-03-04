package ru.nsu.fit.chirikhin;

import java.io.PrintStream;
import java.util.Arrays;

public class StatPrinterOneFilter implements StatPrinter {

    public StatPrinterOneFilter() {

    }

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
            if (i == stat.length - 1) {
                System.out.println("------------");
            }
        }
    }

    public void printStatistics(Statistics stat) {
        //try (PrintStream printStream = new PrintStream(System.out)) {
         //   printStream.format()
        //}
        System.out.printf("")
        System.out.println(stat.getDescription() + " - " + stat.getNumOfLines() + " lines in " + stat.getNumOfFiles() + " files");
    }
}
