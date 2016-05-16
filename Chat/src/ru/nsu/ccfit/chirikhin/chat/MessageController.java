package ru.nsu.ccfit.chirikhin.chat;

public interface MessageController {
    void acceptMessage(ClientMessage clientMessage) throws InterruptedException;
}
