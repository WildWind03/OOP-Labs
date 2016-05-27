package ru.nsu.ccfit.chirikhin.chat;

import java.io.Closeable;

public interface MessageSender extends Closeable {
    void send(Message message);
}
