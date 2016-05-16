package ru.nsu.ccfit.chirikhin.chat;

import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;

public interface ServerMessage {
    void process(ClientMessageController clientMessageController);
}
