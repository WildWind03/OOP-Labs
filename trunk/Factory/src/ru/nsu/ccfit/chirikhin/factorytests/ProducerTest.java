package ru.nsu.ccfit.chirikhin.factorytests;

import org.junit.Test;
import ru.nsu.ccfit.chirikhin.factory.CarBody;
import ru.nsu.ccfit.chirikhin.factory.EngineProducer;

public class ProducerTest {
    public ProducerTest() {

    }

    @Test
    public void produceTest() throws InstantiationException, IllegalAccessException {
        EngineProducer<CarBody> producer = new EngineProducer<>();
        //CarBody carBody = producer.produce();
    }
}
