package ru.nsu.fit.chirikhin;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;

class FileManager {

    public static LinkedList <File> getFullListOfFilesInDirectory(String directoryPath) throws NullPointerException {
        LinkedList<File> fileList = new LinkedList<>();
        File file;

        try {
            file = new File(directoryPath);
        }
        catch (NullPointerException e){
            throw new RuntimeException("System Error! Can't create a file by null reference!", e);
        }

        try {
            if (file.isDirectory()) {
                for (final File entryFile : file.listFiles()) {

                    if (Files.isSymbolicLink(entryFile.toPath())) {
                        break;
                    }

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
            throw new SecurityException("Can't get access to the directory! Security error!", e);
        }
        catch (NullPointerException e) {
            throw new RuntimeException("System Error! Null reference!", e);
        }
        return fileList;
    }
}
