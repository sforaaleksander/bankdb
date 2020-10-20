package com.codecool.bank_db.tables;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class CardsGenerator extends UniqueDataGenerator {
    LinkedList<Long> setOfCardNumbers = createSetOfCardNumbers();
    Random r = new Random();

    public CardsGenerator(Integer recordCount) {
        super(recordCount);
    }

    @Override
    public String generate() {
        StringBuilder mainString = new StringBuilder();
        for (int i = 1; i <= AccountsGenerator.recordCount; i++) {
            if (AccountsGenerator.availableIndexes.isEmpty()){
                break;
            }
            int account_id = getAccountId();
            String pin_code = getPinCode();
            String start_date = getStartDate();
            String expire_date = getExpireDate(start_date);
            String card_number = getCardNumber();
            String ccv_code = getCCV();
            boolean is_active = getIsActive();
            String defaultString = String.format("insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active)" +
                    " values (%d, %s, %s, %s, %s, %s, %b)\n", account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active);
            mainString.append(defaultString);
        }
        return mainString.toString();
    }

    private LinkedList<Long> createSetOfCardNumbers() {
        Set<Long> cardNumbers = new HashSet<>();
        while (cardNumbers.size() <= AccountsGenerator.recordCount) {
            cardNumbers.add(getLongNumber());
        }
        return new LinkedList<>(cardNumbers);
    }

    private String getCardNumber() {
        Long number = setOfCardNumbers.poll();
        return String.valueOf(number);
    }

    private String getExpireDate(String start_date) {
        long dateOfStart = Timestamp.valueOf(start_date).getTime();
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
        long diff = end - offset;
        Timestamp dateOfExpire = new Timestamp(dateOfStart + 4 * diff);
        return dateOfExpire.toString();
    }

    private String getStartDate() {
        long offset = Timestamp.valueOf("2010-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand.toString();
    }

    private int getAccountId() {
        return AccountsGenerator.availableIndexes.poll();
    }

    private boolean getIsActive() {
        return r.nextInt(111) < 100;
    }

    private String getCCV() {
        return String.valueOf(getRandomNumberInRange(111, 999));
    }

    private String getPinCode() {
        return String.valueOf(r.nextInt(10)) + r.nextInt(10) + (r.nextInt(10)) + (r.nextInt(10));
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return r.nextInt((max - min) + 1) + min;
    }

    private long getLongNumber() {
        return 6400000000000000L + (r.nextLong() % 100000000000000L);
    }
}
