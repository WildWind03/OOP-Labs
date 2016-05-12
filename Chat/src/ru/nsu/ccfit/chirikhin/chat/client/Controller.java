package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    private final ClientViewController clientViewController;

    public Controller(ClientViewController clientViewController) {
        this.clientViewController = clientViewController;
    }
}
