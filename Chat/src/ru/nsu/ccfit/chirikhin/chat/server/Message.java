package ru.nsu.ccfit.chirikhin.chat.server;

import java.io.Serializable;

public abstract class Message implements Serializable {
    abstract void process(MessageController messageController);
}
