package ru.nsu.fit.chirikhin;

import java.io.*;
import java.util.Scanner;

class LinesCounter {

    public static int getNumberOfLines(File file) throws FileNotFoundException {

        Scanner myScanner;

        try {
            myScanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("System error! Can't find a file " + file.getPath());
        }

        int counter = 0;

        try {
            while(myScanner.hasNextLine()) {
                myScanner.nextLine();
                counter++;
            }
        }
        catch(Exception e) {
            throw new RuntimeException("System error! Scanner doesn't work!");
        }

        return counter;
    }
}
