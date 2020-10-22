package com.codecool.bank_db.generators;

public class TransactionTypeGenerator extends DataGenerator {

    public TransactionTypeGenerator() {
        super(3);
    }

    @Override
    public String generate() {
        return """
                insert into transaction_types(name)
                values ('transfer');
                insert into transaction_types(name)
                values ('card_payment');
                insert into transaction_types(name)
                values ('atm_transaction');
                """;
    }
}
