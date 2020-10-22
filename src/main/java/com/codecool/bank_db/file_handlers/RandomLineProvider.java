package com.codecool.bank_db.file_handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class RandomLineProvider {
    private final Random random;
    private Scanner scanner;
    private File f;

    public RandomLineProvider(String fileName) {
        File f = new File(fileName);
        this.random = new Random();
        scanner = null;
        initScanner(f);
    }

    private void initScanner(File f) {
        try {
            this.scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getRandomLine() {
        String result = null;
        int n = 0;
        while (scanner.hasNext()) {
            ++n;
            String line = scanner.nextLine();
            if (random.nextInt(n) == 0)
                result = line;
        }
        return result;
    }
}
