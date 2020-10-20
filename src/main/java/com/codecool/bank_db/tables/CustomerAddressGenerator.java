package com.codecool.bank_db.tables;

import java.util.Random;

public class CustomerAddressGenerator extends DataGenerator {
    Random r = new Random();

    public CustomerAddressGenerator(Integer count) {
        super(count);
    }

    @Override
    public String generate() {
        StringBuilder mainString = new StringBuilder();
        for (int i = 1; i <= CustomerGenerator.recordCount; i++) {
            if (CustomerGenerator.availableIndexes.isEmpty() || AddressGenerator.availableIndexes.isEmpty()) {
                break;
            }
            int customer_id = getCustomerID();
            int address_id = getAddressID();
            int address_type_id = 2; // create zameldowania address
            String defaultString = String.format("insert into customers_addresses(customer_id, address_id, address_type_id)" +
                    " values (%d, %d, %d)\n", customer_id, address_id, address_type_id);
            mainString.append(defaultString);
            if (AddressGenerator.availableIndexes.isEmpty()) {
                break;
            }
            if (r.nextInt(100) < 25) { // create additional korespondencyjny address
                int second_address_id = getAddressID();
                int second_address_type_id = 1;
                String additionalAddress = String.format("insert into customers_addresses(customer_id, address_id, address_type_id)" +
                        " values (%d, %d, %d)\n", customer_id, second_address_id, second_address_type_id);
                mainString.append(additionalAddress);
            }
        }
        return mainString.toString();
    }

    private int getCustomerID() {
        return CustomerGenerator.availableIndexes.poll();
    }

    private int getAddressID() {
        return AddressGenerator.availableIndexes.poll();
    }
}
