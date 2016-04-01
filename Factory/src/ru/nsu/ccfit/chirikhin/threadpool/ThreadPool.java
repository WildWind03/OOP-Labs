package ru.nsu.ccfit.chirikhin.threadpool;

import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

import java.util.ArrayList;

public class ThreadPool {

    final private int DEFAULT_SIZE_OF_BLOCKING_QUEUE = 200;

    class ThreadPoolThread extends Thread {
        public ThreadPoolThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Runnable task = tasks.pop();
                    task.run();
                }
            }
            catch(InterruptedException interruptedException) {

            }
        }
    }

    private BlockingQueue<Runnable> tasks;
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

    public void addTask(Runnable task) throws InterruptedException {
        tasks.put(task);
    }

    public void stop() throws InterruptedException {
        for (Thread thread: threads) {
            thread.interrupt();
        }

        for (Thread thread: threads) {
            thread.join();
        }
    }

    public void join() throws InterruptedException {
        for (Thread thread: threads) {
            thread.join();
        }
    }
}
