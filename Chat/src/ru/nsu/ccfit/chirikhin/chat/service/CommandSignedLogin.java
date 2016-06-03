package ru.nsu.ccfit.chirikhin.chat.service;

import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class CommandSignedLogin implements ClientMessage{
    private final String session;
    private final CommandLogin commandLogin;


    public CommandSignedLogin(CommandLogin commandLogin, String session) {
        if (null == commandLogin || null == session) {
            throw new NullPointerException("Null args");
        }

        this.commandLogin = commandLogin;
        this.session = session;
    }

    @Override
    public void process(ServerMessageController messageController) {
        if (null == messageController) {
            throw new NullPointerException("Null serverMessageController");
        }

        messageController.handleLoginMessage(commandLogin, session);
    }
}
