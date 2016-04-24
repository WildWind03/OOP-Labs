package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class AccessoryProducer implements Runnable{

    private final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());
    private final String name;
    private final IDRegisterer idRegisterer;
    private final Storage<Accessory> storage;
    private int producingSpeed;

    private boolean isRunning;

    public AccessoryProducer(Storage<Accessory> storage, String name, int producingSpeed, IDRegisterer idRegisterer) {
        isRunning = true;

        if (null == storage || null == name || null == idRegisterer) {
            String text = name + ": can't create myself because of null reference!";
            logger.fatal(text);
            throw new NullPointerException(text);
        }

        if (producingSpeed < 0) {
            throw new IllegalArgumentException("Producing speed is negative!");
        }

        this.name = name;
        this.storage = storage;
        this.idRegisterer = idRegisterer;
        this.producingSpeed = producingSpeed;

        logger.info(getName() + " has been created! Producing speed is " + producingSpeed);
    }

    public void changeProducingSpeed(int producingSpeed) {
        if (producingSpeed < 0) {
            throw new IllegalArgumentException("Can't change producing speed! It can't be negative!");
        }

        logger.info(getName() + " changed producing speed to " + producingSpeed);
        this.producingSpeed = producingSpeed;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while(isRunning) {
            try {
                Accessory accessory = new Accessory(idRegisterer.getId());
                storage.add(accessory);
                logger.info(getName() + ": new accessory has been produced successfully! Its ID is " + accessory.getId());
                Thread.sleep(producingSpeed);
            } catch (StorageOverflowedException e) {
                logger.error(getName() + "Can not produce accessory. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal(getName() + "Can not produce accessory! Interrupt exception!");
            }
        }
    }

    public void kill() {
        isRunning = false;
    }
}
