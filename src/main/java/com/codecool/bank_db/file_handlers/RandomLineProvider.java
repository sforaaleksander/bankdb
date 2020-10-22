package com.codecool.bank_db.file_handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class RandomLineProvider {
    public String getRandomLine(String fileName) {
        File f = new File(fileName);
        String result = null;
        Random rand = new Random();
        int n = 0;
        Scanner sc = null;
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNext()) {
            ++n;
            String line = sc.nextLine();
            if (rand.nextInt(n) == 0)
                result = line;
        }
        sc.close();
        return result;
    }
}
