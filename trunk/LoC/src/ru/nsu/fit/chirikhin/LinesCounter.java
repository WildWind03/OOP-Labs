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
    public static int getNumberOfLines(File file) throws IOException {
        int counter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                ++counter;
            }
            return counter;
        } catch (IOException e) {
            throw new IOException("Can't get number of lines in " + file.getPath());
        }
        /*try(Scanner myScanner = new Scanner(file)) {
                while (myScanner.hasNextLine()) {
                    myScanner.nextLine();
                    counter++;
                }
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException("LinesCounter: System error! Can't find a file " + file.getPath());
        }
        */
    }
}
