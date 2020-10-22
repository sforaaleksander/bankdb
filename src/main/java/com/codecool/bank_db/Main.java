package com.codecool.bank_db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String... args) throws IOException {
        long start = System.nanoTime();
        RandomDict dict = RandomDict.load("/usr/share/dict/american-english");
        final int count = 1000000;
        for (int i = 0; i < count; i++)
            dict.nextWord();
        long time = System.nanoTime() - start;
        System.out.printf("Took %.3f seconds to load and find %,d random words.", time / 1e9, count);
    }
}

class RandomDict {
    public static final String[] NO_STRINGS = {};
    final Random random = new Random();
    final String[] words;

    public RandomDict(String[] words) {
        this.words = words;
    }

    public static RandomDict load(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        Set<String> words = new LinkedHashSet<String>();
        try {
            for (String line; (line = br.readLine()) != null; ) {
                if (line.indexOf('\'') >= 0) continue;
                words.add(line.toLowerCase());
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
