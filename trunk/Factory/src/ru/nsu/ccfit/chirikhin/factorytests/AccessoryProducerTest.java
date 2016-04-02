package ru.nsu.ccfit.chirikhin.factorytests;

import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.AccessoryProducer;
import ru.nsu.ccfit.chirikhin.factory.AccessoryStorage;

public class AccessoryProducerTest {
    public AccessoryProducerTest() {

    }

    @Test
    public void produceTest() throws InstantiationException, IllegalAccessException {
        AccessoryProducer accessoryProducer = new AccessoryProducer(new AccessoryStorage(10));
    }
}
