package com.codecool.bank_db.generators;

import java.util.Collections;
import java.util.LinkedList;

public abstract class UniqueDataGenerator extends DataGenerator{
    protected LinkedList<Integer> availableIndexes;

    public UniqueDataGenerator(Integer recordCount) {
        super(recordCount);
        availableIndexes = new LinkedList<>();
        createAvailableIndexes();
    }

    public void createAvailableIndexes(){
        for (int i=1; i <= recordCount; i++) {
            availableIndexes.add(i);
        }
        Collections.shuffle(availableIndexes);
    }

    public LinkedList<Integer> getAvailableIndexes() {
        return availableIndexes;
    }
}
