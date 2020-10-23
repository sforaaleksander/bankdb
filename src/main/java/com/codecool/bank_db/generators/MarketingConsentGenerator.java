package com.codecool.bank_db.generators;

import java.io.PrintWriter;

public class MarketingConsentGenerator extends DataGenerator {

    public MarketingConsentGenerator() {
        super(4);
    }

    public void generate(PrintWriter writer) {
        writer.println("""
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (false, false);
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (false, true);
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (true, false);
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (true, true);
                """);
    }
}
