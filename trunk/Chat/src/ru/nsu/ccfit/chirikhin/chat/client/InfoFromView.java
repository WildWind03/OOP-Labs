package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class InfoFromView {
    private static final Logger logger = Logger.getLogger(InfoFromView.class.getName());
    private final Info info;
    private final Object object;

    public InfoFromView(Info info, Object object) {
        if (null == info) {
            throw new NullPointerException("Null pointer exception instead of Info");
        }

        this.info = info;
        this.object = object;
    }

    public Info getInfo() {
        return info;
    }

    public Object getObject() {
        return object;
    }
}
