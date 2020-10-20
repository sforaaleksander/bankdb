package com.codecool.bank_db.tables;

import java.util.Random;

public class TransactionGenerator extends DataGenerator {

    public TransactionGenerator(Integer count) {
        super(count);
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int accountId;
        String date;
        int amount;
        int transactionType;
        for (int i=0; i<recordCount;i++) {
            if (CustomerGenerator.availableIndexes.isEmpty()){
                break;
            }
            accountId = AccountGenerator.availableIndexes.poll();
            date = "";
            amount = random.nextInt(10_000_000);
            transactionType = random.nextInt(5);
            String command = String.format("insert into transactions (%d, %s, %s, %s);\n",
                    accountId, date, amount, transactionType);
            sb.append(command);
        }
        return sb.toString();
    }
}
