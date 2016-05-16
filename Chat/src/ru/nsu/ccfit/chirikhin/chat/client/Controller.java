package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    private final ClientViewController clientViewController;
    private User user;

    public Controller(ClientViewController clientViewController) {
        this.clientViewController = clientViewController;

        clientViewController.addObserver((o, arg) -> {
            InfoFromView infoFromView = (InfoFromView) arg;

            switch (infoFromView.getInfo()) {
                case LOGIN:
                    ClientProperties clientProperties = (ClientProperties) ((InfoFromView) arg).getObject();

                    try {
                        user = new User(clientProperties);
                        user.addEventObserver(clientViewController);
                        clientViewController.onLoggedInSuccessfully();
                    } catch (Exception e) {
                        clientViewController.onLoggedInFailed();
                    }

                    break;
                case TYPED_MESSAGE:
                    logger.info("Controller. Send message");
                    String message = (String) ((InfoFromView) arg).getObject();
                    user.sendMessage(message);
                    break;
            }
        });
    }

    public void disconnectUser() {
        if (null != user) {
            user.disconnect();
        }
    }
}
