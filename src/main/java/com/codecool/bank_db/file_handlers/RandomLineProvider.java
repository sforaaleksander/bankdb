package com.codecool.bank_db.file_handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class RandomLineProvider {
    private final Random random;
    private final File f;
    private Scanner scanner;

    public RandomLineProvider(String fileName) {
        f = new File(fileName);
        this.random = new Random();
    }

    private void initScanner() {
        try {
            this.scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getRandomLine() {
        String result = " ";
        int n = 0;
        initScanner();
        while (scanner.hasNext()) {
            ++n;
            String line = scanner.nextLine();
            if (random.nextInt(n) == 0)
                result = line;
        }
        scanner.close();
        return result;
    }
}
