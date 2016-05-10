package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class ClientMessage extends Message {
    private static final Logger logger = Logger.getLogger(ClientMessage.class.getName());



    @Override
    public void process(MessageController messageController) {
        BlockingQueue<UserMessageStore> userMessagesStores = messageController.getUserMessageStores();
        for (UserMessageStore store : userMessagesStores) {
            store.addMessage(this);
        }
    }
}
