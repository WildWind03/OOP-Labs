package ru.nsu.ccfit.chirikhin.factory;

public class AccessoryProducer implements Runnable {

    private CarDetailStorage<Accessory> storage;
    private ProducingSpeed producingSpeed;

    public AccessoryProducer(CarDetailStorage<Accessory> storage, ProducingSpeed producingSpeed) {
        this.storage = storage;
        this.producingSpeed = producingSpeed;
    }

    public AccessoryProducer(CarDetailStorage<Accessory> storage) {
        this(storage, ProducingSpeed.NORMAL);
    }

    @Override
    public void run() {
        while(true) {
            try {
                storage.addDetail(new Accessory());
            } catch (StorageOverflowedException e) {
                //e.printStackTrace();
            }
        }
    }
}
