package com.codecool.bank_db.tables;

public class ProvincesGenerator extends DataGenerator {

    public ProvincesGenerator() {
        super(16);
    }

    public String generate() {
        return """
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
                """;
    }
}