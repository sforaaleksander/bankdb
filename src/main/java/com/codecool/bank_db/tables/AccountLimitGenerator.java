package com.codecool.bank_db.tables;

import java.util.Random;

public class AccountLimitGenerator extends UniqueDataGenerator{

    public AccountLimitGenerator(Integer recordCount) {
        super(recordCount);
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
        int[] multipliers = new int[]{10, 100, 1000, 10_000};
        for (int i=0; i<recordCount;i++){
            if (CustomerGenerator.availableIndexes.isEmpty()){
                break;
            }
            customerId = CustomerGenerator.availableIndexes.poll();
            daily_withdraw_limit = random.nextInt(10) * multipliers[random.nextInt(4)];
            daily_contactless_limit = random.nextInt(10) * multipliers[random.nextInt(4)];
            daily_transactions_limit = random.nextInt(10) * multipliers[random.nextInt(4)];
            daily_card_payments_limit = random.nextInt(10) * multipliers[random.nextInt(4)];

            String command = String.format("insert into account_limits (%d, %d, %d, %d, %d);\n",
                                    customerId, daily_withdraw_limit, daily_contactless_limit,
                                    daily_transactions_limit, daily_card_payments_limit);
            sb.append(command);
        }
        return sb.toString();
    }
}
