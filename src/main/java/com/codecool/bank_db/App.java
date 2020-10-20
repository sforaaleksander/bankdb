package com.codecool.bank_db;

import com.codecool.bank_db.tables.*;

public class App {
    public static void main(String[] args) {
        CustomerGenerator customerGenerator = new CustomerGenerator(1000);
        AccountGenerator accountGenerator = new AccountGenerator(1000);
        System.out.println(accountGenerator.generate());
    }
}
