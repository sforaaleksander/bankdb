package com.codecool.bank_db.generators;


import java.io.PrintWriter;

public abstract class DataGenerator {
    protected Integer recordCount;

    public DataGenerator(Integer count) {
        recordCount = count;
    }

    public abstract void generate(PrintWriter writer);

    public Integer getRecordCount() {
        return recordCount;
    }
}
