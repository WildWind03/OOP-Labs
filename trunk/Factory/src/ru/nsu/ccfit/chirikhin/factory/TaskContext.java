package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;


public class TaskContext {

    OnResultHandler handler;

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

    private Logger logger = Logger.getLogger(TaskContext.class.getName());

    private final MyRunnable task;
}
