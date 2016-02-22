package ru.nsu.fit.chirikhin;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by cas on 20.02.16.
 */
class FileManager {

    public static LinkedList <File> getFullListOfFilesInDirectory(String directoryPath) throws NullPointerException{
        LinkedList<File> fileList = new LinkedList<>();
        File file = new File(directoryPath);

        if (file.isDirectory()){
            for (final File entryFile : file.listFiles()) {
                if (entryFile.isFile()){
                    fileList.add(entryFile);
                }

                if (entryFile.isDirectory()) {
                    LinkedList <File> filesInSubDir = getFullListOfFilesInDirectory(entryFile.getPath());
                    fileList.addAll(filesInSubDir);
                }
            }
        }
        else {
            throw new IllegalArgumentException("The path is not a directory!");
        }
        return fileList;
    }
}
