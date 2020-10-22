package com.codecool.bank_db.generators;

public class AddressTypeGenerator extends DataGenerator {

    public AddressTypeGenerator() {
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
