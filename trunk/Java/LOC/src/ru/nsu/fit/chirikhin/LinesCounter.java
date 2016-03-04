package ru.nsu.fit.chirikhin;

import java.io.*;
import java.util.Scanner;

class LinesCounter {

    public static int getNumberOfLines(File file) throws FileNotFoundException {
        int counter = 0;

        try(Scanner myScanner = new Scanner(file)) {
                while (myScanner.hasNextLine()) {
                    myScanner.nextLine();
                    counter++;
                }
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException("System error! Can't find a file " + file.getPath());
        }
        catch (Exception e) {
            throw new RuntimeException("System error! Scanner doesn't work!", e);
        }

        return counter;
    }
}
