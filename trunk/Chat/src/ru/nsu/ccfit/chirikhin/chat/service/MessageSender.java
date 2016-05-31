package ru.nsu.ccfit.chirikhin.chat.service;

import java.io.Closeable;
import java.io.IOException;

public interface MessageSender extends Closeable {
    void send(Message message) throws IOException;
}
