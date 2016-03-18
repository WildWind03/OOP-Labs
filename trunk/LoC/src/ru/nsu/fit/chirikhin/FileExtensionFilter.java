package ru.nsu.fit.chirikhin;

import java.io.File;

/**
 * This class is used to filter files by their extensions
 */

public class FileExtensionFilter implements BaseFilter {

    final private String extension;
    /**
     * Creates FileExtensionFilter with specified extension
     * @param extension Extension which filter will filter
     */
    public FileExtensionFilter(String extension) {
        if (null == extension) {
            throw new NullPointerException("FileExtensionFilter: Can't create FileExtensionFilter because the extension is null reference!");
        }

        this.extension = extension;
    }

    /**
     *
     * @param file A file you want to check if it has a filter extension
     * @return true if it is, otherwise false
     */
    public boolean isAppropriate(File file) {
        if (null == file) {
            throw new NullPointerException("FileExtensionFilter: Can't solve if file appropriates or not because the file is null reference!");
        }

        String filePath = file.getPath();
        String extension = null;

        int i = filePath.lastIndexOf(".");

        if (-1 == i || filePath.length() - 1 == i) {
            return this.extension.equals("");
        }

        int p = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf("\\"));

        if (i > p) {
            extension = filePath.substring(i + 1);
        }
        else {
            return this.extension.equals("");
        }

        return this.extension.equals(extension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileExtensionFilter that = (FileExtensionFilter) o;

        return extension != null ? extension.equals(that.extension) : that.extension == null;

    }

    @Override
    public int hashCode() {
        return extension != null ? extension.hashCode() : 0;
    }

    /**
     * @return Returns a description of filter which is often used for some output
     */



    public String getDescriptionForOutput() {
        return extension;
    }
}
