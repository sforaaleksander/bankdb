package com.codecool.bank_db.generators;

import java.io.PrintWriter;
import java.util.Random;

public class TransactionGenerator extends DataGenerator {
    private CustomerGenerator customerGenerator;
    private AccountGenerator accountGenerator;

    public TransactionGenerator(Integer count) {
        super(count);
    }


    public void setCustomerGenerator(CustomerGenerator customerGenerator) {
        this.customerGenerator = customerGenerator;
    }

    public void setAccountGenerator(AccountGenerator accountGenerator) {
        this.accountGenerator = accountGenerator;
    }

    @Override
    public void generate(PrintWriter writer) {
        Random random = new Random();
        int accountId;
        String date;
        int amount;
        int transactionType;
        for (int i=0; i<recordCount;i++) {
            if (customerGenerator.getAvailableIndexes().isEmpty()){
                break;
            }
            accountId = accountGenerator.getAvailableIndexes().poll();
            date = "";
            amount = random.nextInt(10_000_000);
            transactionType = random.nextInt(5);
            String command = String.format("insert into transactions" +
                            " (account_id, date, amount, transaction_type_id)" +
                            " values (%d, '%s', %s, %s);\n",
                    accountId, date, amount, transactionType);
            writer.println(command);
        }
    }
}
