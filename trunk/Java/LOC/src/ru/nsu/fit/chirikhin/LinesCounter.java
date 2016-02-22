package ru.nsu.fit.chirikhin;

import java.io.*;
import java.util.Scanner;

/**
 * Created by cas on 21.02.16.
 */
class LinesCounter {

    public static int getNumberOfLines(File file) throws FileNotFoundException {
        Scanner myScanner = new Scanner(file);
        int counter = 0;

        while(myScanner.hasNextLine()) {
            myScanner.nextLine();
            counter++;
        }

        return counter;
    }
}
