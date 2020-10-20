package com.codecool.bank_db;

import com.codecool.bank_db.tables.*;

public class App {
    public static void main(String[] args) {
        new CardsGenerator(1000);
        new MarketinConsentsGenerator();
        new AddressTypesGenerator();
        new ProvincesGenerator();
        new AddressesGenerator(1000);

        CustomersGenerator customersGenerator = new CustomersGenerator(1000);
        AccountLimitsGenerator accountLimitsGenerator = new AccountLimitsGenerator(1000);
        System.out.println(accountLimitsGenerator.generate());
    }
}
