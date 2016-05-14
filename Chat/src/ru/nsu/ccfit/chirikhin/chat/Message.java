package ru.nsu.ccfit.chirikhin.chat;

import ru.nsu.ccfit.chirikhin.chat.server.MessageController;

import java.io.Serializable;

public abstract class Message implements Serializable {
    abstract public void process(MessageController messageController);
}
