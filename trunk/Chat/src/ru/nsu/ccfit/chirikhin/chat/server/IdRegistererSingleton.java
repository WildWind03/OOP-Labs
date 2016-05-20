package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class IdRegistererSingleton {
    private static final Logger logger = Logger.getLogger(IdRegistererSingleton.class.getName());

    private static class SingletonHolder {
        public static IdRegisterer instance = new IdRegisterer();
    }

    public static IdRegisterer getInstance() {
        return SingletonHolder.instance;
    }

    private IdRegistererSingleton() {

    }
}
