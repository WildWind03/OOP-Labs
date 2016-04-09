package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class AccessoryProducer extends Producer {

    private static final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());

    private Storage<Accessory> storage;

    public AccessoryProducer(Storage<Accessory> storage, ProducingSpeed producingSpeed) {
        super(producingSpeed);
        this.storage = storage;
    }

    public AccessoryProducer(Storage<Accessory> storage) {
        this(storage, ProducingSpeed.NORMAL);
    }

    @Override
    public void run() {
        while(true) {
            try {
                storage.add(new Accessory());
                logger.info("AccessoryProducer: New detail has been produced successfully!");
                Thread.sleep(getTimeToSleep());
            } catch (StorageOverflowedException e) {
                logger.error("AccessoryProducer: Can not produce accessory. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal("AccessoryProducer: Can not produce accessory! Interrupt exception!");
            } catch (DeveloperBugException e) {
                logger.fatal("AccessoryProducer: Can not produce accessory! The developer is stupid!");
            }
        }
    }
}
