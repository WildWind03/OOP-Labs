package ru.nsu.ccfit.chirikhin.factorytests;

import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.CarBody;
import ru.nsu.ccfit.chirikhin.factory.Producer;

public class ProducerTest {
    public ProducerTest() {

    }

    @Test
    public void produceTest() throws InstantiationException, IllegalAccessException {
        Producer<CarBody> producer = new Producer<>();
        //CarBody carBody = producer.produce();
    }
}
