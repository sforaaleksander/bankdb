package com.codecool.bank_db;

import com.codecool.bank_db.tables.CardsGenerator;

import java.math.BigInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new CardsGenerator(BigInteger.valueOf(1000)).getIndexesAvailable();
    }
}
