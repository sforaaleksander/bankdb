package com.codecool.bank_db.tables;

public abstract class DataGenerator {
    protected Integer recordCount;

    public DataGenerator(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public abstract String generate();
}
