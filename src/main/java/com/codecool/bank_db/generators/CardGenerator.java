package com.codecool.bank_db.generators;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CardGenerator extends UniqueDataGenerator {
    private final ThreadLocalRandom random;
    private LinkedList<Long> setOfCardNumbers;
    private AccountGenerator accountGenerator;
    private AtmGenerator atmGenerator;

    public CardGenerator(Integer recordCount) {
        super(recordCount);
        random = ThreadLocalRandom.current();
    }

    public void setAccountGenerator(AccountGenerator accountGenerator) {
        this.accountGenerator = accountGenerator;
    }

    public void setAtmGenerator(AtmGenerator atmGenerator) {
        this.atmGenerator = atmGenerator;
    }

    @Override
    public void generate(PrintWriter writer) {
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
                    " values (%d, '%s', '%s', '%s', '%s', '%s', %b);\n", account_id, pin_code, start_date, expire_date, card_number, cvv_code, is_active);
            writer.print(defaultString);
            for (int i = 1; i < random.nextInt(5, 31); i++) {
                writer.print(createTransactions(account_id, pseudoCardSerial, start_date, expire_date, transactionId));
                transactionId++;
            }
        }
    }

    private int getAccountId() {
        return accountGenerator.getAvailableIndexes().poll();
    }

    private String getPinCode() {
        return String.format("%04d", random.nextInt(10000));
    }

    private String getStartDate() {
        long offset = Timestamp.valueOf("2010-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
        Timestamp rand = new Timestamp(random.nextLong(offset, end));
        return rand.toString();
    }

    private String getExpireDate(String start_date) {
        long dateOfStart = Timestamp.valueOf(start_date).getTime();
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
        long diff = end - offset;
        Timestamp dateOfExpire = new Timestamp(dateOfStart + 4 * diff);
        return dateOfExpire.toString();
    }

    private String getCardNumber() {
        return String.valueOf(setOfCardNumbers.poll());
    }

    private String getCvv() {
        return String.format("%03d", random.nextInt(1000));
    }

    private boolean getIsActive() {
        return random.nextInt(111) < 100;
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

    private int getTransactionType() {
        return random.nextInt(3) + 1;
    }

    private String getTransactionDate(String startDate, String expireDate) {
        long offset = Timestamp.valueOf(startDate).getTime();
        long end = Timestamp.valueOf(expireDate).getTime();
        Timestamp rand = new Timestamp(random.nextLong(offset, end));
        return rand.toString();
    }

    private int getAmount(int transactionType) {
        if (transactionType == 3) { // transfer = 1 / card_payment = 2 / atm_transaction = 3
            int randomAmount = random.nextInt(-1000000, 1000001);
            return randomAmount - randomAmount % 1000;
        }
        return random.nextInt(-1000000, -99);
    }

    private String createTransfer(int transactionID, int doNotUseThisAccountID, String transactionDate) {
        int recipientAccountID;
        do {
            recipientAccountID = random.nextInt(1, accountGenerator.getRecordCount() + 1);
        } while (recipientAccountID == doNotUseThisAccountID);
        String title = generateRandomTitle(transactionDate, recipientAccountID, doNotUseThisAccountID, transactionID);// max 100 characters
        return String.format("insert into transfers(transaction_id, recipient_account_id, title)" +
                " values (%d, %d, '%s');\n", transactionID, recipientAccountID, title);
    }

    private String generateRandomTitle(String transactionDate, int recipientAccountID, int doNotUseThisAccountID, int transactionID) {
        return String.format("t-%s-from-%d-to-%d-on-%s", transactionID, doNotUseThisAccountID, recipientAccountID, transactionDate);
    }

    private String createCardPayment(int transactionID, int cardID) {
        String recipientName = generateRandomRecipient(); //max 50 znakow
        return String.format("insert into card_payments(transaction_id, card_id, recipient_name)" +
                " values (%d, %d, '%s');\n", transactionID, cardID, recipientName);
    }

    private String generateRandomRecipient() {
        return String.format("random-recipient-%d", random.nextInt(100_000, 1_000_000));
    }

    private String createAtmTransaction(int transactionID, int cardID) {
        int atmID = random.nextInt(1, atmGenerator.getRecordCount() + 1);
        return String.format("insert into atm_transactions(transaction_id, card_id, atm_id)" +
                " values (%d, %d, %d);\n", transactionID, cardID, atmID);
    }

    public void setSetOfCardNumbers() {
        Set<Long> cardNumbers = new HashSet<>();
        while (cardNumbers.size() <= accountGenerator.getRecordCount()) {
            cardNumbers.add(getRandomCardNumber());
        }
        this.setOfCardNumbers = new LinkedList<>(cardNumbers);
    }

    private long getRandomCardNumber() {
        return random.nextLong(6400000000000000L, 6500000000000000L);
    }
}
