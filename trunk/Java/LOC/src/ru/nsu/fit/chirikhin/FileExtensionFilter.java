package ru.nsu.fit.chirikhin;

import java.io.File;

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
            try {
                extension = filePath.substring(i + 1);
            }
            catch (IndexOutOfBoundsException e) {
                throw new RuntimeException ("System Error! Can't get extension of file!", e);
            }
        }

        return this.extension.equals(extension);
    }

    public String getDescriptionForOutput() {
        return extension;
    }
}
