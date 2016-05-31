package ru.nsu.ccfit.chirikhin.chat.service;

import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public interface ClientMessage extends Message {
    void process(ServerMessageController messageController) throws MessageProcessException;
}
