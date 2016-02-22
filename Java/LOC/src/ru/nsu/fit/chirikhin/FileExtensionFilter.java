package ru.nsu.fit.chirikhin;

import java.io.File;

/**
 * Created by cas on 16.02.16.
 */
public class FileExtensionFilter implements BaseFilter {

    private String extension = "";
    final private static String filterId = "Ext";
    FileExtensionFilter(String extension) {
        this.extension = extension;
    }

    public boolean isAppropriate(File file) {
        String filePath = file.getPath();
        String extension = "";

        int i = filePath.lastIndexOf(".");
        int p = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf("\\"));

        if (i > p) {
            extension = filePath.substring(i + 1);
        }

        return this.extension.equals(extension);
    }

    public String getDescriptionForOutput() {
        return filterId + " " + extension;
    }
}
