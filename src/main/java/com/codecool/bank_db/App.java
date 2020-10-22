package com.codecool.bank_db;

import com.codecool.bank_db.file_handlers.FileWriter;

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
