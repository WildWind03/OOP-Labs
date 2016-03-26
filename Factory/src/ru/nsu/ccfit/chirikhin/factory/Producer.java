package ru.nsu.ccfit.chirikhin.factory;

import java.util.Objects;

public class Producer <Item> {
    public Producer() {

    }

    Item create(Class<Item> itemClass) throws IllegalAccessException, InstantiationException {
        return itemClass.newInstance();
    }

    Item produce() {
        Item item = create();
        return  item;
    }
}
