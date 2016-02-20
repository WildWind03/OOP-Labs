package ru.nsu.fit.chirikhin;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by cas on 20.02.16.
 */
public class FileManager {

    private final String path;

    FileManager(String path) {
        this.path = path;
    }

    public String[] getFullListOfFilesInDirectory(String directoryPath) {
        LinkedList<File> fileList = new LinkedList<>();
        File file = new File(directoryPath);

        if (file.isDirectory()){
            for (final File entryFile : file.listFiles()) {
                if (entryFile.isFile()){
                    fileList.add(entryFile);
                }

            }
        }
        else{
            throw new
        }
        return new String[1];
    }
}
