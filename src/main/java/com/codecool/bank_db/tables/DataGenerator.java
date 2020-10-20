package com.codecool.bank_db.tables;

public abstract class DataGenerator {
    static Integer recordCount;

    public DataGenerator(Integer count) {
        recordCount = count;
    }

    public abstract String generate();
}
