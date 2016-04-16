package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class TaskContext {

    public TaskContext(MyRunnable task, OnResultHandler resultHandler, OnErrorHandler errorHandler) {
        this.task = task;
        this.resultHandler = resultHandler;
        this.errorHandler = errorHandler;
    }

    public MyRunnable getTask() {
        return task;
    }

    public OnResultHandler getResultHandler() {
        return resultHandler;
    }

    public OnErrorHandler getErrorHandler() {
        return errorHandler;
    }

    private Logger logger = Logger.getLogger(TaskContext.class.getName());

    private final MyRunnable task;
    private final OnResultHandler resultHandler;
    private final OnErrorHandler errorHandler;
}
