package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

public class IdRegisterer {
    private static final Logger logger = Logger.getLogger(IdRegisterer.class.getName());

    private final AtomicLong counter = new AtomicLong(0);

    public long getNewId() {
        return counter.getAndIncrement();
    }

}
