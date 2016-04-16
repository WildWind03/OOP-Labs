package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

public class SwingView implements Observer {
    private Logger logger = Logger.getLogger(SwingView.class.getName());

    @Override
    public void update(Observable observable, Object o) {
        switch(observable.getClass().getName()) {
            case "Factory" :
                break;
        }
    }
}
