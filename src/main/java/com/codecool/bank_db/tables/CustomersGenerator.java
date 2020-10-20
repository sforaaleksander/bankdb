package com.codecool.bank_db.tables;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class CustomersGenerator extends UniqueDataGenerator {
    private ThreadLocalRandom random;

    public CustomersGenerator(Integer recordCount) {
        super(recordCount);
        random = ThreadLocalRandom.current();
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();

        Customers customers = new Customers(recordCount);
        for (int i = 0; i < recordCount; i++) {
            sb.append(generateOne(customers)).append("\n");
        }
        return sb.toString();
    }

    private String generateOne(Customers customers) {
        String first_name, last_name, phone_number, email, password, pesel;
        int marketing_cons_id, bank_branch_id;
        boolean male = random.nextBoolean();
        first_name = male // TODO use db to generate names
                ? Customers.MALE_NAMES[random.nextInt(0, Customers.MALE_NAMES.length)]
                : Customers.FEMALE_NAMES[random.nextInt(0, Customers.FEMALE_NAMES.length)];
        last_name = male
                ? Customers.MALE_SURNAMES[random.nextInt(0, Customers.MALE_SURNAMES.length)]
                : Customers.FEMALE_SURNAMES[random.nextInt(0, Customers.FEMALE_SURNAMES.length)];
        password = generateRandomString(20);
        marketing_cons_id = random.nextInt(1, MarketinConsentsGenerator.recordCount);
        bank_branch_id = 0; // random.nextInt(1, BankBranchGenerator.recordCount); TODO uncomment this

        do {
            phone_number = "" + random.nextLong(500_000_000L, 900_000_000L);
            email = first_name + "_" + last_name + random.nextInt(0, 100);
            pesel = generatePesel(male);
        } while (customers.getPhoneNumbers().contains(phone_number) ||
                customers.getEmails().contains(email) ||
                customers.getPesels().contains(pesel));

        customers.addPhoneNumber(phone_number);
        customers.addEmail(email);
        customers.addPesel(pesel);

        return String.format("insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)\n" +
                "VALUES('%s', '%s', '%s', '%s', '%s', '%s', %s, %s);", first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id);
    }

    private String generateRandomString(int stringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public String generatePesel(boolean male) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime.now().atZone(zoneId).toEpochSecond();

        long youngestPossible = LocalDateTime.now().minusYears(18).atZone(zoneId).toEpochSecond();
        long oldestPossible = LocalDateTime.now().minusYears(110).atZone(zoneId).toEpochSecond();

        LocalDateTime birthDate = LocalDateTime.ofEpochSecond(random.nextLong(oldestPossible, youngestPossible), 0, ZoneOffset.ofHours(0));

        String pesel = ""
                + String.format("%02d", birthDate.getYear() % 100)
                + String.format("%02d", birthDate.getYear() < 2000 ? birthDate.getMonthValue() : birthDate.getMonthValue() + 20)
                + String.format("%02d", birthDate.getDayOfMonth())
                + String.format("%03d", random.nextInt(0, 1000))
                + (male ? random.nextInt(0, 5) * 2 + 1 : random.nextInt(0, 5) * 2);
        pesel += calculateLastPeselDigit(pesel);
        return pesel;
    }

    public String calculateLastPeselDigit(String pesel) {
        int checksum = 0;
        int[] multiples = {1, 3, 7, 9};
        for (int i = 0; i < 10; i++) {
            checksum += Integer.parseInt(pesel.substring(i, i + 1)) * multiples[i % multiples.length];
        }
        return String.valueOf((10 - checksum % 10) % 10);
    }
}
