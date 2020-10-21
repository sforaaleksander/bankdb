package com.codecool.bank_db.tables;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AccountGenerator extends UniqueDataGenerator {
    private CustomerGenerator customerGenerator;

    public AccountGenerator(Integer recordCount) {
        super(recordCount);
    }

    public void setCustomerGenerator(CustomerGenerator customerGenerator) {
        this.customerGenerator = customerGenerator;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        LinkedList<String> accountNumbers = generateAccountNumbers();
        int customerId;
        boolean isActive;
        for (int i = 0; i < recordCount; i++) {
            if (customerGenerator.getAvailableIndexes().isEmpty()) {
                break;
            }
            customerId = customerGenerator.getAvailableIndexes().poll();
            String accountNo = accountNumbers.poll();
            long availableBalance = getAvailableBalance(random);
            long bookingBalance = getBookingBalanceByAvailable(random, availableBalance);
            Timestamp dateOpened = generateDate(Timestamp.valueOf("2010-01-01 00:00:00"));
            isActive = random.nextInt(11) < 10;
            if (!isActive) {
                Timestamp dateClosed = generateDate(dateOpened);
                String command = String.format("insert into accounts " +
                                " (customer_id, account_number, available_balance, booking_balance, date_opened, date_closed, is_active) " +
                                " values (%d, '%s', %d, %d, '%s', '%s', %s);\n",
                        customerId, accountNo, availableBalance, bookingBalance, dateOpened, dateClosed, false);
                sb.append(command);
            } else {
                String command = String.format("insert into accounts " +
                                " (customer_id, account_number, available_balance, booking_balance, date_opened, is_active) " +
                                " values (%d, '%s', %d, %d, '%s', %s);\n",
                        customerId, accountNo, availableBalance, bookingBalance, dateOpened, true);
                sb.append(command);
            }
        }
        customerGenerator.createAvailableIndexes();
        return sb.toString();
    }

    private long getBookingBalanceByAvailable(Random random, long availableBalance) {
        boolean balancesAreEqual = random.nextInt(11) < 9;
        if (balancesAreEqual) return availableBalance;
        if (availableBalance == 0) return 0;
        long difference = Math.abs(random.nextLong() % availableBalance);
        return availableBalance + difference;
    }

    private long getAvailableBalance(Random random) {
        int richnessIndicator = random.nextInt(1001);
        if (richnessIndicator == 1000) {
            return Math.abs(random.nextLong());
        } else if (richnessIndicator > 900) {
            return random.nextInt(100_000_000);
        } else if (richnessIndicator > 600) {
            return random.nextInt(1_000_000);
        } else return random.nextInt(100_000);
    }

    private LinkedList<String> generateAccountNumbers() {
        Set<String> numbers = new HashSet<>();
        while (numbers.size() != recordCount) {
            numbers.add(generateAccountNumberAsString());
        }
        return new LinkedList<>(numbers);
    }

    private String generateAccountNumberAsString() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long first = random.nextLong(1000000000000L, 10000000000000L);
        long second = random.nextLong(1000000000000L, 10000000000000L);
        return "" + first + second;
    }

    private Timestamp generateDate(Timestamp startingDate) {
        long offset = startingDate.getTime();
        long end = Timestamp.valueOf("2020-10-20 00:00:00").getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long) (Math.random() * diff));
    }
}
