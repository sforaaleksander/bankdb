package com.codecool.bank_db;

import com.codecool.bank_db.tables.AddressTypesGenerator;
import com.codecool.bank_db.tables.CardsGenerator;
import com.codecool.bank_db.tables.MarketinConsentsGenerator;
import com.codecool.bank_db.tables.ProvincesGenerator;

public class App {
    public static void main(String[] args) {
        new CardsGenerator(1000).getAvailableIndex();
        new MarketinConsentsGenerator();
        new AddressTypesGenerator();
        new ProvincesGenerator();
    }
}
