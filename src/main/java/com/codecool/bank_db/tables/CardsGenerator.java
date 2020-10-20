package com.codecool.bank_db.tables;

import java.util.*;

public class CardsGenerator extends UniqueDataGenerator {

    public CardsGenerator(Integer recordCount) {
        super(recordCount);
    }

    @Override
    public String generate() {
        return null;
    }

    @Override
    public List<Integer> getIndexesAvailable() {
        List<Integer> indexesAvailable = new LinkedList<>();
        for (int i=0; i < 10_000_000; i++) {
            indexesAvailable.add(i);
        }
        Collections.shuffle(indexesAvailable);
        for (Integer e : indexesAvailable) {
            System.out.print(e);
        }
        return indexesAvailable;
    }
}
