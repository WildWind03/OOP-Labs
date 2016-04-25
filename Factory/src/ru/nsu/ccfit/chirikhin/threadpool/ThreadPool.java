package ru.nsu.ccfit.chirikhin.threadpool;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;
import ru.nsu.ccfit.chirikhin.factory.TaskContext;

import java.util.ArrayList;


public class ThreadPool {

    final private int DEFAULT_SIZE_OF_BLOCKING_QUEUE = 200;

    public class ThreadPoolThread extends Thread {
        final private Logger logger = Logger.getLogger(ThreadPool.class.getName());

        public ThreadPoolThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            TaskContext taskContext;

            try {
                while (!isInterrupted()) {
                    taskContext = tasks.pop();
                    taskContext.getTask().run();
                    taskContext.handle();
                }
            } catch(InterruptedException interruptedException) {
                logger.fatal("Interrupted exception");
            }

        }
    }

    private BlockingQueue<TaskContext> tasks;
    private ArrayList<ThreadPoolThread> threads;

    public ThreadPool(int threadCount) throws InterruptedException {

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Can't create threadPool! ThreadCount is invalid!");
        }

        threads = new ArrayList<>();
        tasks = new BlockingQueue<>(DEFAULT_SIZE_OF_BLOCKING_QUEUE);

        for (int i = 0; i < threadCount; ++i) {
            ThreadPoolThread threadPoolThread = new ThreadPoolThread("Pool thread number " + i);
            threadPoolThread.start();
            threads.add(threadPoolThread);
        }
    }

    public void addTask(TaskContext taskContext) throws InterruptedException {
        tasks.put(taskContext);
    }

    public void stop() throws InterruptedException {
        threads.forEach(Thread::interrupt);

        for (Thread thread: threads) {
            thread.join();
        }
    }
}
