package ru.nsu.ccfit.chirikhin.chat;

public class SignedClientLoginMessage implements ClientMessage{
    private final long sessionId;
    private final LoginMessage loginMessage;


    public SignedClientLoginMessage(LoginMessage loginMessage, long sessionId) {
        this.loginMessage = loginMessage;
        this.sessionId = sessionId;
    }

    @Override
    public void process(ServerMessageController messageController) {
        messageController.handleLoginMessage(loginMessage, sessionId);
    }
}
