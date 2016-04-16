package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class TaskContext {

    public TaskContext(MyRunnable task, Handler resultHandler, Handler errorHandler) {
        this.task = task;
        this.resultHandler = resultHandler;
        this.errorHandler = errorHandler;
    }

    public MyRunnable getTask() {
        return task;
    }

    public Handler getResultHandler() {
        return resultHandler;
    }

    public Handler getErrorHandler() {
        return errorHandler;
    }

    private Logger logger = Logger.getLogger(TaskContext.class.getName());

    MyRunnable task;
    Handler resultHandler;
    Handler errorHandler;
}
