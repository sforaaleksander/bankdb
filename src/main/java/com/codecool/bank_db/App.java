package com.codecool.bank_db;

import com.codecool.bank_db.fileWriter.FileWriter;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            new FileWriter().collectGeneratedInsertsToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
