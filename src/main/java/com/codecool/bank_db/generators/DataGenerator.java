package com.codecool.bank_db.generators;

public abstract class DataGenerator {
    protected Integer recordCount;

    public DataGenerator(Integer count) {
        recordCount = count;
    }

    public abstract String generate();

    public Integer getRecordCount() {
        return recordCount;
    }
}
