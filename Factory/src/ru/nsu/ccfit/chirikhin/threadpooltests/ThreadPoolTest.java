package ru.nsu.ccfit.chirikhin.threadpooltests;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;
import ru.nsu.ccfit.chirikhin.factory.MyRunnable;
import ru.nsu.ccfit.chirikhin.factory.OnErrorHandler;
import ru.nsu.ccfit.chirikhin.factory.OnResultHandler;
import ru.nsu.ccfit.chirikhin.factory.TaskContext;
import ru.nsu.ccfit.chirikhin.threadpool.ThreadPool;


public class ThreadPoolTest {
    public ThreadPoolTest() {
    }

    class Printer implements MyRunnable {
        private BlockingQueue<String> blockingQueue;
        private String str;

        public Printer(BlockingQueue<String> blockingQueue, String str) {
            this.blockingQueue = blockingQueue;
            this.str = str;
        }

        @Override
        public int run() {
            try {
                blockingQueue.put(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(str);

            return 1;
        }
    }


    @Test
    public void threadPoolTest() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new BlockingQueue<>(8);
        Printer printer1 = new Printer(blockingQueue, "1");
        Printer printer2 = new Printer(blockingQueue, "2");
        Printer printer3 = new Printer(blockingQueue, "3");
        Printer printer4 = new Printer(blockingQueue, "4");
        Printer printer5 = new Printer(blockingQueue, "5");
        Printer printer6 = new Printer(blockingQueue, "6");
        Printer printer7 = new Printer(blockingQueue, "7");
        Printer printer8 = new Printer(blockingQueue, "8");

        ThreadPool threadPool = new ThreadPool(3);
    }

}
