package ru.nsu.ccfit.chirikhin.factoryTests;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.*;

public class CarDetailStorageTest {
    @Test
    public void isEmptyTest() {
        CarDetailStorage<CarDetail> carDetailStorage = new CarDetailStorage<>(2);
        Assert.assertTrue(carDetailStorage.isEmpty());
    }

    @Test
    public void isFullTest() throws StorageOverflowedException {
        CarDetailStorage<CarDetail> carDetailStorage = new CarDetailStorage<>(2);
        carDetailStorage.addDetail(new Engine());
        carDetailStorage.addDetail(new Engine());
        Assert.assertTrue(carDetailStorage.isFull());
    }

    @Test (expected = StorageOverflowedException.class)
    public void storageOverflowed() throws StorageOverflowedException, StorageEmptyException {
        CarDetailStorage<CarDetail> carDetailStorage = new CarDetailStorage<>(2);
        carDetailStorage.addDetail(new Engine());
        carDetailStorage.addDetail(new Engine());
        carDetailStorage.addDetail(new Engine());

        Assert.assertEquals(carDetailStorage.getMaxSize(), 2);
        Assert.assertEquals(0, carDetailStorage.getNextDetail().getId());
        Assert.assertEquals(1, carDetailStorage.getNextDetail().getId());
    }

    @Test
    public void checkIdAndMaxSize() throws StorageOverflowedException, StorageEmptyException {
        CarDetailStorage<CarDetail> carDetailStorage = new CarDetailStorage<>(2);
        carDetailStorage.addDetail(new Engine());
        carDetailStorage.addDetail(new Engine());

        Assert.assertEquals(carDetailStorage.getMaxSize(), 2);
        Assert.assertEquals(0, carDetailStorage.getNextDetail().getId());
        Assert.assertEquals(1, carDetailStorage.getNextDetail().getId());
    }

}
