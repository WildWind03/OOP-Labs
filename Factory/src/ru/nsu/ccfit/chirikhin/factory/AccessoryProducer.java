package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class AccessoryProducer extends Producer {

    private static final Logger logger =  Logger.getLogger(AccessoryProducer.class.getName());

    final private String name;
    final private String NULL_REFERENCE_ERROR_STR = "Can't create myself because of null reference!";

    private Storage<Accessory> storage;

    public AccessoryProducer(Storage<Accessory> storage, String name, ProducingSpeed producingSpeed) {
        super(producingSpeed);

        if (null == storage || null == name || null == producingSpeed) {
            String text = name + " " + NULL_REFERENCE_ERROR_STR;
            logger.fatal(text);
            throw new IllegalArgumentException(text);
        }

        this.name = name;
        this.storage = storage;

        logger.info(getName() + " has been created! Producing speed is " + producingSpeed);
    }

    public void changeProducingSpeed(ProducingSpeed producingSpeed) {
        if (null == producingSpeed) {
            String text = getName() + " can not change producing speed because of null reference";
            throw new IllegalArgumentException(text);
        }

        super.changeProducingSpeed(producingSpeed);

        logger.info(getName() + " changed producing speed to " + producingSpeed);
    }

    public AccessoryProducer(Storage<Accessory> storage, String name) {
        this(storage, name, ProducingSpeed.NORMAL);
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
                Thread.sleep(getTimeToSleep());
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
