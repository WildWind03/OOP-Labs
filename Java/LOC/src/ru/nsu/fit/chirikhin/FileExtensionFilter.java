package ru.nsu.fit.chirikhin;

/**
 * Created by cas on 16.02.16.
 */
public class FileExtensionFilter implements BaseFilter {

    private String[] params;
    FileExtensionFilter(String[] filterParams) {
        params = filterParams;
    }

    public boolean isAppropriate() {
        return true;
    }
}
