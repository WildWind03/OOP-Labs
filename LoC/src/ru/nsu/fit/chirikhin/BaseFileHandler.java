package ru.nsu.fit.chirikhin;

import java.io.File;
import java.io.IOException;

public interface BaseFileHandler {
    void handle(File file) throws IOException;
}
