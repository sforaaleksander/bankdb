package com.codecool.bank_db.generators;

import java.io.PrintWriter;

public class ProvinceGenerator extends DataGenerator {

    public ProvinceGenerator() {
        super(16);
    }

    public void generate(PrintWriter writer) {
        writer.println("""
                insert into provinces(name)
                values ('dolnośląskie');
                insert into provinces(name)
                values ('kujawsko-pomorskie');
                insert into provinces(name)
                values ('lubelskie');
                insert into provinces(name)
                values ('lubuskie');
                insert into provinces(name)
                values ('łódzkie');
                insert into provinces(name)
                values ('małopolskie');
                insert into provinces(name)
                values ('mazowieckie');
                insert into provinces(name)
                values ('opolskie');
                insert into provinces(name)
                values ('podkarpackie');
                insert into provinces(name)
                values ('podlaskie');
                insert into provinces(name)
                values ('pomorskie');
                insert into provinces(name)
                values ('śląskie');
                insert into provinces(name)
                values ('świętokrzyskie');
                insert into provinces(name)
                values ('warmińsko-mazurskie');
                insert into provinces(name)
                values ('wielkopolskie');
                insert into provinces(name)
                values ('zachodniopomorskie');
                """);
    }
}