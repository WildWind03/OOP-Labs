package ru.nsu.fit.chirikhin;

import java.io.File;

/**
 * This class is used to filter files by their extensions
 */

public class FileExtensionFilter implements BaseFilter {

    private String extension = "";
    final private static String filterId = "Ext";

    /**
     * Creates FileExtensionFilter with specified extension
     * @param extension Extension which filter will filter
     */
    public FileExtensionFilter(String extension) {
        this.extension = extension;
    }

    /**
     *
     * @param file A file you want to check if it has a filter extension
     * @return true if it is, otherwise false
     */
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

    /**
     * @return Returns a description of filter which is often used for some output
     */

    public String getDescriptionForOutput() {
        return extension;
    }
}
