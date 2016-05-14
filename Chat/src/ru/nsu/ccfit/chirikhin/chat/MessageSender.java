package ru.nsu.ccfit.chirikhin.chat;

import ru.nsu.ccfit.chirikhin.chat.Message;

public interface MessageSender {
    void send(Message message);
}
