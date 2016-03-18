package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * It's used to handle files in a directory
 */

public class FileManager {

    static int curRecursionDepth = 0;
    static final int MAX_RECURSION_DEPTH = 1000;

    /**
     * @param dir A Path where you want to find and handle files
     * @param fileHandler A Handler that will handle files im the directory
     * @throws SecurityException if it is impossible to get access to the directory
     */

    public static void handleFilesInDirectory(String dir, BaseFileHandler fileHandler) throws IOException {
        if (null == dir || null == fileHandler) {
            throw new NullPointerException("File Manager: Error! Null references in handlesFilesInDirectory!");
        }
        if (curRecursionDepth < MAX_RECURSION_DEPTH) {
            curRecursionDepth++;

            File file = new File(dir);

            if (!file.exists()) {
                throw new IllegalArgumentException("File Manager: Error! There isn't directory " + dir);
            }

            try {
                if (file.isDirectory()) {
                    for (final File entryFile : file.listFiles()) {

                        if (Files.isSymbolicLink(entryFile.toPath())) {
                            System.out.println("File Manager: Warning! SymLink " + entryFile.toPath() + " has been found. It will be ignored!");
                            break;
                        }

                        if (entryFile.isFile()) {
                            fileHandler.handle(entryFile);
                        }

                        if (entryFile.isDirectory()) {
                            handleFilesInDirectory(entryFile.getPath(), fileHandler);
                        }
                    }
                }
            }
            catch (SecurityException e) {
                throw new SecurityException("File Manager: Can't get access to the directory " + dir + ". Security error!", e);
            }
            catch (NullPointerException e) {
                throw new NullPointerException("File Manager: null pointer exception while listing files!");
            }

            curRecursionDepth--;
        }
        else {
            System.out.println("File Manager: Warning! Can't open the directory " + dir + " because the limit of recursion is reached! The limit is " + MAX_RECURSION_DEPTH + ". All the files in the directory will be ignored!");
        }
    }
}