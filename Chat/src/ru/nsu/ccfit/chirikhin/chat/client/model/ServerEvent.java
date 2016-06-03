package ru.nsu.ccfit.chirikhin.chat.client.model;

import ru.nsu.ccfit.chirikhin.chat.client.view.ClientViewController;

public interface ServerEvent {
    void process(ClientViewController clientViewController);
}
