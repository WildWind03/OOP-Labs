package ru.nsu.fit.chirikhin;

import java.io.File;
import java.util.LinkedList;

class FileManager {

    public static LinkedList <File> getFullListOfFilesInDirectory(String directoryPath) throws NullPointerException {
        LinkedList<File> fileList = new LinkedList<>();
        File file;
        try {
            file = new File(directoryPath);
        }
        catch (NullPointerException e){
            throw new RuntimeException("System Error! Can't create a file by null reference!");
        }

        try {
            if (file.isDirectory()) {
                for (final File entryFile : file.listFiles()) {
                    if (entryFile.isFile()) {
                        fileList.add(entryFile);
                    }

                    if (entryFile.isDirectory()) {
                        LinkedList<File> filesInSubDir = getFullListOfFilesInDirectory(entryFile.getPath());
                        fileList.addAll(filesInSubDir);
                    }
                }
            } else {
                throw new IllegalArgumentException("The path is not a directory!");
            }
        }
        catch (SecurityException e) {
            throw new SecurityException("Can't get access to the directory! Security error!");
        }
        catch (NullPointerException e) {
            throw new RuntimeException("System Error! Null reference!");
        }
        return fileList;
    }
}
