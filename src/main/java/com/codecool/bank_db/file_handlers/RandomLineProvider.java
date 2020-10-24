package com.codecool.bank_db.file_handlers;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLineProvider {
    private final FileChannel channel;
    private final ThreadLocalRandom random;
    private final int maxLineLength;

    public RandomLineProvider(String filePath) throws IOException {
        random = ThreadLocalRandom.current();
        channel = new FileInputStream(new File(filePath)).getChannel();
        maxLineLength = getMaxLineLength(filePath);
    }

    private int getMaxLineLength(String filename) throws IOException {
        int max = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (String line; (line = br.readLine()) != null; ) {
                if (line.length() > max) {
                    max = line.length();
                }
            }
        }
        return max + 3;
    }

    public String getRandomLine() throws IOException {
        String string = "";
        ByteBuffer buffer;
        long startPosition = random.nextLong(channel.size() - 1);

        while (!string.equals("\n") && startPosition > 0) {
            buffer = ByteBuffer.allocate(1);
            channel.read(buffer, startPosition--);
            string = new String(buffer.array(), StandardCharsets.UTF_8);
        }

        startPosition = startPosition != 0 ? startPosition + 2 : startPosition;

        buffer = ByteBuffer.allocate(maxLineLength);
        channel.read(buffer, startPosition);

        return new String(buffer.array(), StandardCharsets.UTF_8).split("\n")[0];
    }
}
