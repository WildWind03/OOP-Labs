package ru.nsu.ccfit.chirikhin.chat.server;

import java.io.InputStream;

public interface MessageSerializer {
    Message read(InputStream inputStream);
}
