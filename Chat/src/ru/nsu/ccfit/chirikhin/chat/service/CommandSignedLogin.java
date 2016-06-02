package ru.nsu.ccfit.chirikhin.chat.service;

import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class CommandSignedLogin implements ClientMessage{
    private final String session;
    private final CommandLogin commandLogin;


    public CommandSignedLogin(CommandLogin commandLogin, String session) {
        this.commandLogin = commandLogin;
        this.session = session;
    }

    @Override
    public void process(ServerMessageController messageController) {
        messageController.handleLoginMessage(commandLogin, session);
    }
}
