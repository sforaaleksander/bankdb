package com.codecool.bank_db.tables;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class CardGenerator extends UniqueDataGenerator {
    private Random r;
    private LinkedList<Long> setOfCardNumbers;
    private AccountGenerator accountGenerator;
    private AtmGenerator atmGenerator;

    public CardGenerator(Integer recordCount) {
        super(recordCount);
        r = new Random();
    }

    public void setAccountGenerator(AccountGenerator accountGenerator) {
        this.accountGenerator = accountGenerator;
    }

    public void setAtmGenerator(AtmGenerator atmGenerator) {
        this.atmGenerator = atmGenerator;
    }

    @Override
    public String generate() {
        StringBuilder mainString = new StringBuilder();
        int transactionId = 1;
        for (int pseudoCardSerial = 1; pseudoCardSerial <= accountGenerator.getRecordCount(); pseudoCardSerial++) {
            if (accountGenerator.getAvailableIndexes().isEmpty()) {
                break;
            }
            int account_id = getAccountId();
            String pin_code = getPinCode();
            String start_date = getStartDate();
            String expire_date = getExpireDate(start_date);
            String card_number = getCardNumber();
            String cvv_code = getCvv();
            boolean is_active = getIsActive();
            String defaultString = String.format("insert into cards(account_id, pin_code, start_date, expire_date, card_number, cvv_code, is_active)" +
                    " values (%d, %s, '%s', '%s', '%s', '%s', %b);\n", account_id, pin_code, start_date, expire_date, card_number, cvv_code, is_active);
            mainString.append(defaultString);
            mainString.append(createTransactions(account_id, pseudoCardSerial, start_date, expire_date, transactionId));
            transactionId++;
        }
        return mainString.toString();
    }

    private String createTransactions(int accountID, int pseudoCardSerial, String startDate, String expireDate, int transactionId) { //todo transfer = 1 / card_payment = 2 / atm_transaction = 3
        StringBuilder finalString = new StringBuilder();
        int transactionType = getTransactionType();
        String transactionDate = getTransactionDate(startDate, expireDate);
        int amount = getAmount(transactionType);

        String transactionString = String.format("insert into transactions(id, account_id, date, amount, transaction_type_id)" +
                " values (%d, %d, '%s', %d, %d);\n", transactionId, accountID, transactionDate, amount, transactionType);
        finalString.append(transactionString);
        if (transactionType == 1) {
            finalString.append(createTransfer(transactionId, accountID, transactionDate));
        } else if (transactionType == 2) {
            finalString.append(createCardPayment(transactionId, pseudoCardSerial));
        } else if (transactionType == 3) {
            finalString.append(createAtmTransaction(transactionId, pseudoCardSerial));
        }
        return finalString.toString();
    }

    private String createAtmTransaction(int transactionID, int cardID) {
        int atmID = getRandomNumberInRange(1, atmGenerator.getRecordCount());
        return String.format("insert into atm_transactions(transaction_id, card_id, atm_id)" +
                " values (%d, %d, %d);\n", transactionID, cardID, atmID);
    }

    private String createCardPayment(int transactionID, int cardID) {
        String recipientName = generateRandomRecipient(); //max 50 znakow
        return String.format("insert into card_payments(transaction_id, card_id, recipient_name)" +
                " values (%d, %d, '%s');\n", transactionID, cardID, recipientName);
    }

    private String generateRandomRecipient() {
        int randomNr = getRandomNumberInRange(100_000, 999_999);
        return String.format("random-recipient-%d", randomNr);
    }

    private String createTransfer(int transactionID, int doNotUseThisAccountID, String transactionDate) {
        int recipientAccountID = getRandomNumberInRange(1, accountGenerator.getRecordCount());
        while (recipientAccountID != doNotUseThisAccountID) {
            recipientAccountID = getRandomNumberInRange(1, accountGenerator.getRecordCount());
        }
        String title = generateRandomTitle(transactionDate, recipientAccountID, doNotUseThisAccountID, transactionID);// max 100 znakow
        return String.format("insert into transfers(transaction_id, recipient_account_id, title)" +
                " values (%d, %d, '%s');\n", transactionID, recipientAccountID, title);
    }

    private String generateRandomTitle(String transactionDate, int recipientAccountID, int doNotUseThisAccountID, int transactionID) {
        return String.format("t-%s-from-%d-to-%d-on-%s", transactionID, doNotUseThisAccountID, recipientAccountID, transactionDate);
    }

    private int getAmount(int transactionType) {
        if (transactionType != 3) {
            return getRandomNumberInRange(1, 100000);
        }
        int randomAmount = getRandomNumberInRange(-10000, 10000);
        return randomAmount - randomAmount % 10;
    }

    private String getTransactionDate(String startDate, String expireDate) {
        long offset = Timestamp.valueOf(startDate).getTime();
        long end = Timestamp.valueOf(expireDate).getTime();
        long diff = end - offset;
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand.toString();
    }

    private int getTransactionType() {
        int randomInt = r.nextInt(99);
        if (randomInt < 33) {
            return 1;
        } else if (randomInt < 66) {
            return 2;
        }
        return 3;
    }

    public void setSetOfCardNumbers() {
        Set<Long> cardNumbers = new HashSet<>();
        while (cardNumbers.size() <= accountGenerator.getRecordCount()) {
            cardNumbers.add(getLongNumber());
        }
        this.setOfCardNumbers = new LinkedList<>(cardNumbers);
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
        return accountGenerator.getAvailableIndexes().poll();
    }

    private boolean getIsActive() {
        return r.nextInt(111) < 100;
    }

    private String getCvv() {
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
