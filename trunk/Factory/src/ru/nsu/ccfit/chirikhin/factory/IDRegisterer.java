package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

public class IDRegisterer {

    private final static Logger logger = Logger.getLogger(IDRegisterer.class.getName());
    private final AtomicLong maxId = new AtomicLong(0);
    public IDRegisterer() {

    }

    public long getId(){
        return maxId.getAndIncrement();
    }

    public long getMaxId() {
        return maxId.get() - 1;
    }

}
