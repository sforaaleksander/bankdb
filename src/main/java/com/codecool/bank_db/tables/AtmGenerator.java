package com.codecool.bank_db.tables;

import java.util.Random;

public class AtmGenerator extends DataGenerator {
    private AddressGenerator addressGenerator;

    public AtmGenerator(Integer count) {
        super(count);
    }

    public void setAddressGenerator(AddressGenerator addressGenerator) {
        this.addressGenerator = addressGenerator;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int addressId;
        boolean isBankProperty;
        boolean isActive;
        for (int i=0; i<recordCount;i++) {
            if (addressGenerator.getAvailableIndexes().isEmpty()){
                break;
            }
            addressId = addressGenerator.getAvailableIndexes().poll();
            isBankProperty = random.nextInt(10) < 9;
            isActive = random.nextInt(10) < 9;
            String command = String.format("insert into atms " +
                            " (address_id, is_banks_property, is_active)" +
                            " values (%d, %s, %s);\n", addressId, isBankProperty, isActive);
            sb.append(command);
        }
            return sb.toString();
    }
}
