package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class AccessoryProducer implements Runnable{

    private final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());
    private final String name;
    private final IDRegisterer idRegisterer;
    private final Storage<Accessory> storage;
    private final long producingSpeed;

    public AccessoryProducer(Storage<Accessory> storage, String name, long producingSpeed, IDRegisterer idRegisterer) {

        if (null == storage || null == name || null == idRegisterer) {
            String text = name + ": can't create myself because of null reference!";
            logger.fatal(text);
            throw new IllegalArgumentException(text);
        }

        this.name = name;
        this.storage = storage;
        this.idRegisterer = idRegisterer;
        this.producingSpeed = producingSpeed;

        logger.info(getName() + " has been created! Producing speed is " + producingSpeed);
    }

    public void changeProducingSpeed(long producingSpeed) {
        if (producingSpeed < 0) {
            throw new IllegalArgumentException("Can't change producing speed! It can't be negative!");
        }

        logger.info(getName() + " changed producing speed to " + producingSpeed);
    }

    public AccessoryProducer(Storage<Accessory> storage, String name, IDRegisterer idRegisterer) {
        this(storage, name, 0, idRegisterer);
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Accessory accessory = new Accessory();
                storage.add(accessory);
                logger.info(getName() + "New detail has been produced successfully! Its ID is " + accessory.getId());
                Thread.sleep();
            } catch (StorageOverflowedException e) {
                logger.error(getName() + "Can not produce accessory. Storage is full");
            } catch (InterruptedException e) {
                logger.fatal(getName() + "Can not produce accessory! Interrupt exception!");
            } catch (DeveloperBugException e) {
                logger.fatal(getName() + "Can not produce accessory! The developer is stupid!");
            }
        }
    }
}
