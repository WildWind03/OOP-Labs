package ru.nsu.ccfit.chirikhin.chat;

import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

import java.io.Serializable;

public interface ClientMessage extends Message {
    void process(ServerMessageController messageController);
}
