package ru.nsu.ccfit.chirikhin.chat;

import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

import java.io.Serializable;

public abstract class Message implements Serializable {
    abstract public void process(ServerMessageController serverMessageController);
}
