package ru.nsu.fit.chirikhin;

import java.io.*;
import java.util.Scanner;

/**
 * It's used to count lines in files
 */
public class LinesCounter {

    /**
     * @param file A file which you want to count lines in
     * @return A count of lines in the file
     * @throws FileNotFoundException if it is impossible to get access to the file
     * @throws RuntimeException if it is system error (the error doesn't depend on input)
     */
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
