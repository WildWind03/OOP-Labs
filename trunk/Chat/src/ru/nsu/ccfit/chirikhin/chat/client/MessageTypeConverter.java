package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

public class MessageTypeConverter {
    private static final Logger logger = Logger.getLogger(MessageTypeConverter.class.getName());

    private MessageTypeConverter() {

    }

    public static ClientMessageEnum getMessageType(Message message) {
        if (message instanceof CommandText) {
            return ClientMessageEnum.TEXT;
        }

        if (message instanceof CommandLogin) {
            return ClientMessageEnum.LOGIN;
        }

        if (message instanceof CommandLogout) {
            return ClientMessageEnum.LOGOUT;
        }

        if (message instanceof CommandClientList) {
            return ClientMessageEnum.CLIENT_LIST;
        }

        throw new IllegalArgumentException("There is no such type of messages in enum");
    }
}
