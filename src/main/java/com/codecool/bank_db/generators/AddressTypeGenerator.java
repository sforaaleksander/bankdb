package com.codecool.bank_db.generators;

import java.io.PrintWriter;

public class AddressTypeGenerator extends DataGenerator {

    public AddressTypeGenerator() {
        super(2);
    }

    public void generate(PrintWriter writer) {
        writer.println("""
                insert into address_types(name)
                values ('korespondencyjny');
                insert into address_types(name)
                values ('zameldowania');
                """);
    }
}
