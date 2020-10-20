package com.codecool.bank_db.tables;

import java.util.Collections;
import java.util.LinkedList;

public abstract class UniqueDataGenerator extends DataGenerator{
    protected static LinkedList<Integer> availableIndexes;

    public UniqueDataGenerator(Integer recordCount) {
        super(recordCount);
        availableIndexes = new LinkedList<>();
        createAvailableIndexes();
    }

    private void createAvailableIndexes(){
        for (int i=0; i < recordCount; i++) {
            availableIndexes.add(i);
        }
        Collections.shuffle(availableIndexes);

    }

    public Integer getAvailableIndex() {
        return availableIndexes.poll();
    };
}
