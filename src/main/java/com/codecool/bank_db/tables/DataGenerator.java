package com.codecool.bank_db.tables;

import java.math.BigInteger;

public abstract class DataGenerator {
    protected Integer recordCount;

    public DataGenerator(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public abstract String generate();
}
