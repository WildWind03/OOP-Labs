package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;


public class TaskContext {

    private final OnResultHandler handler;

    private final static Logger logger = Logger.getLogger(TaskContext.class.getName());

    private final MyRunnable task;

    public TaskContext(MyRunnable task, OnResultHandler handler) {
        this.task = task;
        this.handler = handler;
    }

    public void handle() {
        handler.handle();
    }

    public MyRunnable getTask() {
        return task;
    }
}
