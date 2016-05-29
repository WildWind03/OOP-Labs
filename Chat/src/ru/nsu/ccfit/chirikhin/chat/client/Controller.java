package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.CommandClientList;
import ru.nsu.ccfit.chirikhin.chat.CommandText;

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

                    client.sendMessage(new CommandText(message, client.getSessionId()));
                    break;

                case STOP:
                    if (null == client) {
                        break;
                    }

                    client.onStop();

                    break;

                case LOGIN:
                    if (null == client) {
                        throw new NullPointerException("Client is null");
                    }

                    client.login((String) (((InfoFromView) arg).getObject()));
                    break;
                case LIST_OF_USERS:
                    if (null == client) {
                        throw new NullPointerException("Client is null");
                    }

                    logger.info("List of users");
                    client.sendMessage(new CommandClientList(client.getSessionId()));
                    break;
            }
        });
    }
}
