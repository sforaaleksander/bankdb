package com.codecool.bank_db;

import com.codecool.bank_db.tables.*;

public class App {
    public static void main(String[] args) {
        new CardGenerator(1000);
        new MarketinConsentsGenerator();
        new AddressTypeGenerator();
        new ProvinceGenerator();
        new AddressGenerator(1000);

        CustomerGenerator customersGenerator = new CustomerGenerator(1000);
        AccountLimitGenerator accountLimitsGenerator = new AccountLimitGenerator(1000);
        System.out.println(accountLimitsGenerator.generate());
    }
}
