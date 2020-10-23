package com.codecool.bank_db.generators;

import java.io.PrintWriter;

public class BankBranchGenerator extends DataGenerator {
    private AddressGenerator addressGenerator;

    public BankBranchGenerator(Integer count) {
        super(count);
    }

    public void setAddressGenerator(AddressGenerator addressGenerator) {
        this.addressGenerator = addressGenerator;
    }

    @Override
    public void generate(PrintWriter writer) {
        for (int i=0;i<recordCount;i++){
            if (addressGenerator.getAvailableIndexes().isEmpty()) {
                break;
            }
            int addressId = addressGenerator.getAvailableIndexes().poll();
            String command = String.format("insert into bank_branches (address_id) values (%d);\n",
                    addressId);
            writer.println(command);
        }
    }
}
