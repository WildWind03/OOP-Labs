package ru.nsu.ccfit.chirikhin.factorytests;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.*;

public class CarDetailStorageTest {
    @Test
    public void isEmptyTest() {
        Storage<CarDetail> carDetailStorage = new Storage<>(2);
        Assert.assertTrue(carDetailStorage.isEmpty());
    }

    @Test
    public void isFullTest() throws StorageOverflowedException, InterruptedException {
        Storage<CarDetail> carDetailStorage = new Storage<>(2);
        carDetailStorage.add(new Engine());
        carDetailStorage.add(new Engine());
        Assert.assertTrue(carDetailStorage.isFull());
    }

    @Test (expected = StorageOverflowedException.class)
    public void storageOverflowed() throws StorageOverflowedException, StorageEmptyException, InterruptedException {
        Storage<CarDetail> carDetailStorage = new Storage<>(2);
        carDetailStorage.add(new Engine());
        carDetailStorage.add(new Engine());
        carDetailStorage.add(new Engine());

        Assert.assertEquals(carDetailStorage.getMaxSize(), 2);
        Assert.assertEquals(0, carDetailStorage.getNext().getId());
        Assert.assertEquals(1, carDetailStorage.getNext().getId());
    }

    @Test
    public void checkIdAndMaxSize() throws StorageOverflowedException, StorageEmptyException, InterruptedException {
        Storage<CarDetail> carDetailStorage = new Storage<>(2);
        carDetailStorage.add(new Engine());
        carDetailStorage.add(new Engine());

        Assert.assertEquals(carDetailStorage.getMaxSize(), 2);
        Assert.assertEquals(0, carDetailStorage.getNext().getId());
        Assert.assertEquals(1, carDetailStorage.getNext().getId());
    }

}
