package ru.nsu.ccfit.chirikhin.factorytests;

import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.Accessory;
import ru.nsu.ccfit.chirikhin.factory.AccessoryProducer;
import ru.nsu.ccfit.chirikhin.factory.Storage;

public class AccessoryProducerTest {
    public AccessoryProducerTest() {

    }

    @Test
    public void produceTest() throws InstantiationException, IllegalAccessException {
        AccessoryProducer accessoryProducer = new AccessoryProducer(new Storage<Accessory>(10));
    }
}
