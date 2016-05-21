package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ClientListMessage;
import ru.nsu.ccfit.chirikhin.chat.ClientTextMessage;
import ru.nsu.ccfit.chirikhin.chat.TimeoutException;

public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    private Client client = null;

    public Controller(ClientViewController clientViewController) {
        clientViewController.addObserver((o, arg) -> {
            InfoFromView infoFromView = (InfoFromView) arg;

            switch (infoFromView.getInfo()) {
                case CONNECT:
                    ClientProperties clientProperties = (ClientProperties) ((InfoFromView) arg).getObject();

                    try {
                        if (null != client) {
                            client.disconnect();
                        }

                        client = new Client(clientProperties);
                        client.addMessageControllerObserver(clientViewController);
                        clientViewController.onConnectionSetSuccessfully();
                        logger.error("Connected successfully");
                    } catch (Exception e) {
                        logger.error("Can not connect");
                        clientViewController.onConnectionSetFailed();
                    }

                    break;
                case TYPED_MESSAGE:
                    String message = (String) ((InfoFromView) arg).getObject();

                    if (null == client) {
                        throw new NullPointerException("Client is null");
                    }

                    try {
                        client.sendMessage(new ClientTextMessage(message, client.getSessionId()));
                    } catch (TimeoutException e) {
                        clientViewController.onTypedMessageNotDelivered();
                    }
                    break;

                case LOGOUT:

                    if (null == client) {
                        throw new NullPointerException("Client is null");
                    }

                    try {
                        client.onStop();
                    } catch (TimeoutException e) {
                        clientViewController.onExitMessageNotDelivered();
                    } finally {
                        client.disconnect();
                    }
                    break;

                case LOGIN:
                    if (null == client) {
                        throw new NullPointerException("Client is null");
                    }

                    try {
                        client.login((String) (((InfoFromView) arg).getObject()));
                    } catch (TimeoutException e) {
                        clientViewController.onLoginMessageNotDelivered();
                    }
                    break;
                case LIST_OF_USERS:
                    if (null == client) {
                        throw new NullPointerException("Client is null");
                    }

                    try {
                        client.sendMessage(new ClientListMessage(client.getSessionId()));
                    } catch (TimeoutException e) {
                        clientViewController.onListOfUsersMessagesNotDelivered();
                    }
                    break;
            }
        });
    }
}
