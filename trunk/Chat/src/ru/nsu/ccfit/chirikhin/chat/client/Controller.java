package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    private Client client = null;

    public Controller(ClientViewController clientViewController) {
        clientViewController.addObserver((o, arg) -> {
            InfoFromView infoFromView = (InfoFromView) arg;

            switch (infoFromView.getInfo()) {
                case LOGIN:
                    ClientProperties clientProperties = (ClientProperties) ((InfoFromView) arg).getObject();

                    try {
                        client = new Client(clientProperties);
                        client.addMessageControllerObserver(clientViewController);
                        clientViewController.onLoggedInSuccessfully();
                    } catch (Exception e) {
                        clientViewController.onLoggedInFailed();
                    }

                    break;
                case TYPED_MESSAGE:
                    logger.info("Controller. Send message");
                    String message = (String) ((InfoFromView) arg).getObject();
                    client.sendMessage(message);
                    break;
                case DISCONNECT:
                    if (null != client) {
                        client.disconnect();
                    }
                    break;
                case LOGOUT:
                    client.onStop();
                    break;
            }
        });
    }

    public void disconnectUser() {
        if (null != client) {
            client.disconnect();
        }
    }
}
