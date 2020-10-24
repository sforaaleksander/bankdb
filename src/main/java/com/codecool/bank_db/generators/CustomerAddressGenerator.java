package com.codecool.bank_db.generators;

import java.io.PrintWriter;
import java.util.Random;

public class CustomerAddressGenerator extends DataGenerator {
    private final Random random = new Random();
    private CustomerGenerator customerGenerator;
    private AddressGenerator addressGenerator;

    public CustomerAddressGenerator(Integer count) {
        super(count);
    }

    public void setCustomerGenerator(CustomerGenerator customerGenerator) {
        this.customerGenerator = customerGenerator;
    }

    public void setAddressGenerator(AddressGenerator addressGenerator) {
        this.addressGenerator = addressGenerator;
    }

    @Override
    public void generate(PrintWriter writer) {
        for (int i = 1; i <= customerGenerator.getRecordCount(); i++) {
            if (customerGenerator.getAvailableIndexes().isEmpty() || addressGenerator.getAvailableIndexes().isEmpty()) {
                break;
            }
            int customer_id = getCustomerID();
            int address_id = getAddressID();
            int address_type_id = 2; // create zameldowania address
            String defaultString = String.format("insert into customers_addresses(customer_id, address_id, address_type_id)"
                    + " values (%d, %d, %d);\n", customer_id, address_id, address_type_id);
            writer.println(defaultString);
            if (addressGenerator.getAvailableIndexes().isEmpty()) {
                break;
            }
            if (random.nextInt(100) < 25) { // create additional korespondencyjny address
                int second_address_id = getAddressID();
                int second_address_type_id = 1;
                String additionalAddress = String.format("insert into customers_addresses(customer_id, address_id, address_type_id)" +
                        " values (%d, %d, %d);\n", customer_id, second_address_id, second_address_type_id);
                writer.println(additionalAddress);
            }
        }
    }

    private int getCustomerID() {
        return customerGenerator.getAvailableIndexes().poll();
    }

    private int getAddressID() {
        return addressGenerator.getAvailableIndexes().poll();
    }
}
