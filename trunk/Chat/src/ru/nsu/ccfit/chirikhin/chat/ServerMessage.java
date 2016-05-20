package ru.nsu.ccfit.chirikhin.chat;

public interface ServerMessage extends Message {
    void process(ClientMessageController clientMessageController);
}
