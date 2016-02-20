package ru.nsu.fit.chirikhin;

import java.io.File;

/**
 * Created by cas on 16.02.16.
 */
public class FileExtensionFilter implements BaseFilter {

    private String extension;
    FileExtensionFilter(String extension) {
        this.extension = extension;
    }

    public boolean isAppropriate(File file) {
        return true;
    }
}
