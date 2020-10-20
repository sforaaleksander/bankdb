package com.codecool.bank_db.tables;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class UniqueDataGenerator extends DataGenerator{
    protected List<Integer> availableIndexes;
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

    abstract List<Integer> getIndexesAvailable();
}
