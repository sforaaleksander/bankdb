package com.codecool.bank_db.tables;

public class MarketinConsentsGenerator extends DataGenerator {

    public MarketinConsentsGenerator() {
        super(4);
    }

    public String generate() {
        return """
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (false, false);
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (false, true);
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (true, false);
                insert into marketing_consents(electronic_marketing, phone_marketing)
                VALUES (true, true);
                """;
    }
}
