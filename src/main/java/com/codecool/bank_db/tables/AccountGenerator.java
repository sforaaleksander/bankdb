package com.codecool.bank_db.tables;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

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
        LinkedList<BigInteger> accountNumbers = generateAccountNumbers();
        int customerId;
        String isActive;
        for (int i=0; i<recordCount;i++) {
            if (customerGenerator.getAvailableIndexes().isEmpty()){
                break;
            }
            customerId = customerGenerator.getAvailableIndexes().poll();
            BigInteger accountBigInt = accountNumbers.poll();
            String accountNo = accountBigInt.toString();
            String availableBalance = String.valueOf(Math.abs(random.nextLong()));
            String bookingBalance = String.valueOf(Math.abs(random.nextLong()));
            Timestamp dateOpened = generateDate();
            isActive = random.nextInt(11) < 10 ? "true" : "false";
            if (!isActive.equals("true")) {
                Timestamp dateClosed = generateDate();
                String command = String.format("insert into accounts " +
                                " (customer_id, account_number, available_balance, booking_balance, date_opened, date_closed, is_active) " +
                                " values (%d, '%s', '%s', '%s', '%s', '%s', '%s');\n",
                        customerId, accountNo, availableBalance, bookingBalance, dateOpened, dateClosed, isActive);
                sb.append(command);
            } else {
                String command = String.format("insert into accounts " +
                                " (customer_id, account_number, available_balance, booking_balance, date_opened, is_active) " +
                                " values (%d, '%s', '%s', '%s', '%s', '%s');\n",
                        customerId, accountNo, availableBalance, bookingBalance, dateOpened, isActive);
                sb.append(command);
            }
        }
        customerGenerator.createAvailableIndexes();
        return sb.toString();
    }

    private LinkedList<BigInteger> generateAccountNumbers() {
        Set<BigInteger> numbers = new HashSet<>();
        while (numbers.size() != recordCount) {
            numbers.add(generateNumber());
        }
        return new LinkedList<>(numbers);
    }

    //TODO
    private BigInteger generateNumber() {
        BigInteger minLimit = new BigInteger("100000000000000000000000");
        BigInteger maxLimit = new BigInteger("999999999999999999999999");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);
        return res;
    }

    private Timestamp generateDate() {
        long offset = Timestamp.valueOf("2010-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2020-10-20 00:00:00").getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long)(Math.random() * diff));
    }
}
