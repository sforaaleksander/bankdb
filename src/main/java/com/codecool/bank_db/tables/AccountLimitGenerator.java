package com.codecool.bank_db.tables;

import java.util.Random;

public class AccountLimitGenerator extends UniqueDataGenerator{
    private CustomerGenerator customerGenerator;

    public AccountLimitGenerator(Integer recordCount) {
        super(recordCount);
    }

    public void setCustomerGenerator(CustomerGenerator customerGenerator) {
        this.customerGenerator = customerGenerator;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int customerId;
        int daily_withdraw_limit;
        int daily_contactless_limit;
        int daily_transactions_limit;
        int daily_card_payments_limit;
        int[] multipliers = new int[]{10, 10, 1_000, 10_000};
        int[] smallMultipliers = new int[]{1, 10, 10};
        for (int i=0; i<recordCount;i++){
            if (customerGenerator.getAvailableIndexes().isEmpty()){
                break;
            }
            customerId = customerGenerator.getAvailableIndexes().poll();
            daily_withdraw_limit = (random.nextInt(10)+10) * multipliers[random.nextInt(4)];
            daily_contactless_limit = (random.nextInt(10)+10) * multipliers[random.nextInt(4)];
            daily_transactions_limit = (random.nextInt(10)+1) * smallMultipliers[random.nextInt(3)];
            daily_card_payments_limit = (random.nextInt(10)+1) * smallMultipliers[random.nextInt(3)];

            String command = String.format("insert into account_limits values (%d, %d, %d, %d, %d);\n",
                                    customerId, daily_withdraw_limit, daily_contactless_limit,
                                    daily_transactions_limit, daily_card_payments_limit);
            sb.append(command);
        }
        customerGenerator.createAvailableIndexes();
        return sb.toString();
    }
}
