package com.codecool.bank_db.tables;

import java.util.Random;

public class AtmGenerator extends DataGenerator {

    public AtmGenerator(Integer count) {
        super(count);
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int addressId;
        String isBankProperty;
        String isActive;
        for (int i=0; i<recordCount;i++) {
            if (AddressGenerator.availableIndexes.isEmpty()){
                break;
            }
            addressId = AddressGenerator.availableIndexes.poll();
            isBankProperty = random.nextInt(10) < 9 ? "true" : "false";
            isActive = random.nextInt(10) < 9 ? "true" : "false";
            String command = String.format("insert into atms " +
                            " (address_id, is_banks_property, is_active)" +
                            " values (%d, %s, %s);\n", addressId, isBankProperty, isActive);
            sb.append(command);
        }
            return sb.toString();
    }
}
