package ru.nsu.ccfit.chirikhin.chat.service;

import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;

public interface ServerMessage extends Message {
    void process(ClientMessageController clientMessageController);
}
