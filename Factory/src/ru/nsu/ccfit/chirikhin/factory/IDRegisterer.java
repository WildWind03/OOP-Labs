package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

public class IDRegisterer {

    private Logger logger = Logger.getLogger(IDRegisterer.class.getName());
    private AtomicLong maxId;
    public IDRegisterer() {
        maxId = new AtomicLong(0);
    }

    public long getId(){
        return maxId.getAndIncrement();
    }

    public long getMaxId() {
        return maxId.get() - 1;
    }

}
