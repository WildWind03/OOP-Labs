package ru.nsu.ccfit.chirikhin.chat;

public class SignedLoginMessage implements ClientMessage{
    private final long sessionId;
    private final LoginMessage loginMessage;


    public SignedLoginMessage(LoginMessage loginMessage, long sessionId) {
        this.loginMessage = loginMessage;
        this.sessionId = sessionId;
    }

    @Override
    public void process(ServerMessageController messageController) {
        messageController.handleLoginMessage(loginMessage, sessionId);
    }
}
