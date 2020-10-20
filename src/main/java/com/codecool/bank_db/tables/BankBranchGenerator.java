package com.codecool.bank_db.tables;

public class BankBranchGenerator extends DataGenerator {

    public BankBranchGenerator(Integer count) {
        super(count);
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<recordCount;i++){
            if (AddressGenerator.availableIndexes.isEmpty()) {
                break;
            }
            int addressId = AddressGenerator.availableIndexes.poll();
            String command = String.format("insert into bank_branches (address_id) values (%d);\n",
                    addressId);
            sb.append(command);
        }
        return sb.toString();
    }
}
