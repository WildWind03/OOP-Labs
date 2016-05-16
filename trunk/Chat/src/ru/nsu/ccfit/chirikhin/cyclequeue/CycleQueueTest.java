package ru.nsu.ccfit.chirikhin.cyclequeue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class CycleQueueTest {
    private final CycleQueue<Integer> cycleQueue = new CycleQueue<>(5);

    @Before
    public void setUp() throws Exception {
        for (int k = 0; k < 5; ++k) {
            cycleQueue.put(k);
        }
    }

    @Test
    public void putAndGet() throws Exception {
        cycleQueue.put(6);
        LinkedList<Integer> linkedList = cycleQueue.get();

        Assert.assertTrue(linkedList.getFirst() == 1);
        Assert.assertTrue(linkedList.getLast() == 6);
    }
}