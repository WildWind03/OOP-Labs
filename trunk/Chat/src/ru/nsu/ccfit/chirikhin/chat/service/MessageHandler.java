package ru.nsu.ccfit.chirikhin.chat.service;

public interface MessageHandler {
    void handle(Message message) throws InterruptedException;
}
