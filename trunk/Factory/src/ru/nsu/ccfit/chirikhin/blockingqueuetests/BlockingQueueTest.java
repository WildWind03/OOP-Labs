package ru.nsu.ccfit.chirikhin.blockingqueuetests;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.ccfit.chirikhin.blockingqueue.BlockingQueue;

public class BlockingQueueTest {
    class QueuePutter implements Runnable{
        private BlockingQueue<String> blockingQueue;
        public QueuePutter(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            try {
                blockingQueue.put("Hi");
                blockingQueue.put("I'm thread!");
                blockingQueue.pop();
                blockingQueue.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public BlockingQueueTest() {
    }

    @Test
    public void putAndPopTest() throws InterruptedException {
        BlockingQueue blockingQueue = new BlockingQueue(400);
        Thread thread1 = new Thread(new QueuePutter(blockingQueue));
        Thread thread2 = new Thread(new QueuePutter(blockingQueue));
        Thread thread3 = new Thread(new QueuePutter(blockingQueue));
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();


        Assert.assertTrue(blockingQueue.isEmpty());
    }
}
