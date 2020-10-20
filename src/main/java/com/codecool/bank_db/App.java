package com.codecool.bank_db;

import com.codecool.bank_db.tables.*;

public class App {
    public static void main(String[] args) {
        new CardsGenerator(1000).getAvailableIndex();
        new MarketinConsentsGenerator();
        new AddressTypesGenerator();
        new ProvincesGenerator();
        new AddressesGenerator(1000);
    }
}
