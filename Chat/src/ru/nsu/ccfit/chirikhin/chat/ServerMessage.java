package ru.nsu.ccfit.chirikhin.chat;

import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;

import java.io.Serializable;

public interface ServerMessage extends Message {
    void process(ClientMessageController clientMessageController);
}
