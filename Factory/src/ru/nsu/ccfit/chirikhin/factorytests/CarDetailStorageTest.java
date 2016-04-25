package ru.nsu.ccfit.chirikhin.factorytests;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.*;

public class CarDetailStorageTest {
    @Test
    public void isEmptyTest() {
        Storage<Engine> carDetailStorage = new Storage<>(2);
        Assert.assertTrue(carDetailStorage.isEmpty());
    }

    @Test
    public void isFullTest() throws InterruptedException {
        Storage<Engine> carDetailStorage = new Storage<>(2);
        carDetailStorage.add(new Engine(1));
        carDetailStorage.add(new Engine(2));
        Assert.assertTrue(carDetailStorage.isFull());
    }

    @Test
    public void checkIdAndMaxSize() throws  InterruptedException {
        Storage<Engine> carDetailStorage = new Storage<>(2);
        carDetailStorage.add(new Engine(0));
        carDetailStorage.add(new Engine(1));

        Assert.assertEquals(carDetailStorage.getMaxSize(), 2);
        Assert.assertEquals(0, carDetailStorage.getNext().getId());
        Assert.assertEquals(1, carDetailStorage.getNext().getId());
    }

}
