package com.codecool.bank_db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class RandomDict {
    private final String[] NO_STRINGS = {};
    private final Random random = new Random();
    private String[] words;

    private RandomDict(String[] words) {
        this.words = words;
    }

    public RandomDict() {
    }

    public RandomDict load(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        Set<String> words = new LinkedHashSet<>();
        try {
            for (String line; (line = br.readLine()) != null; ) {
                if (line.indexOf('\'') >= 0) continue;
                words.add(line);
            }
        } finally {
            br.close();
        }
        return new RandomDict(words.toArray(NO_STRINGS));
    }

    public String nextWord() {
        return words[random.nextInt(words.length)];
    }
}
