package com.codecool.bank_db.tables;

public class AddressTypesGenerator extends DataGenerator {

    public AddressTypesGenerator() {
        super(2);
    }

    public String generate() {
        return """
                insert into address_types(name)
                values ('korespondencyjny');
                insert into address_types(name)
                values ('zameldowania');
                """;
    }
}
