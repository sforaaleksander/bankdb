package com.codecool.bank_db.generators;

import java.io.PrintWriter;

public class TransactionTypeGenerator extends DataGenerator {

    public TransactionTypeGenerator() {
        super(3);
    }

    @Override
    public void generate(PrintWriter writer) {
        writer.println("""
                insert into transaction_types(name)
                values ('transfer');
                insert into transaction_types(name)
                values ('card_payment');
                insert into transaction_types(name)
                values ('atm_transaction');
                """);
    }
}
