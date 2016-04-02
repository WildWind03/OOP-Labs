package ru.nsu.ccfit.chirikhin.factory;

/**
 * Created by cas on 02.04.16.
 */
abstract public class Producer implements Runnable {
    private ProducingSpeed producingSpeed;

    private final static int SLOW_SPEED = 5000;
    private final static int NORMAL_SPEED = 2000;
    private final static int FAST_SPEED = 500;

    protected int getTimeToSleep() throws DeveloperBugException {
        int time = -1;

        switch(producingSpeed) {
            case NORMAL:
                time = NORMAL_SPEED;
                break;
            case SLOW :
                time = SLOW_SPEED;
                break;
            case FAST :
                time = FAST_SPEED;
                break;
        }

        if (-1 == time) {
            throw new DeveloperBugException("AccessoryProducer: can't calculate period of producing!");
        }

        return time;
    }

    public Producer(ProducingSpeed producingSpeed) {
        this.producingSpeed = producingSpeed;
    }
}
