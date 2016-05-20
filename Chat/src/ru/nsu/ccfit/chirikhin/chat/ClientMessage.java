package ru.nsu.ccfit.chirikhin.chat;

public interface ClientMessage extends Message {
    void process(ServerMessageController messageController) throws MessageProcessException;
}
