package ru.nsu.ccfit.chirikhin.chat;

public interface MessageController {
    void acceptMessage(Message message) throws InterruptedException;
}
