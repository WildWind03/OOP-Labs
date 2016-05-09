package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public interface MessageTransformer {
    public void toBytes(Message message);
}
