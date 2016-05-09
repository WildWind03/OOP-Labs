package ru.nsu.ccfit.chirikhin.chat.server;

public interface Message {
    void process(MessageController messageController);
    byte[] toBytes();
}
