package com.codecool.bank_db.tables;

import com.codecool.bank_db.tables.name_generator.NameGenerator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerGenerator extends UniqueDataGenerator {
    private final ThreadLocalRandom random;
    private final NameGenerator nameGenerator;
    private MarketingConsentGenerator marketingConsentGenerator;
    private BankBranchGenerator bankBranchGenerator;

    public CustomerGenerator(Integer recordCount) {
        super(recordCount);
        random = ThreadLocalRandom.current();
        nameGenerator = new NameGenerator();
    }

    public void setMarketingConsentGenerator(MarketingConsentGenerator marketingConsentGenerator) {
        this.marketingConsentGenerator = marketingConsentGenerator;
    }

    public void setBankBranchGenerator(BankBranchGenerator bankBranchGenerator) {
        this.bankBranchGenerator = bankBranchGenerator;
    }

    @Override
    public String generate() {
//        nameGenerator.dropTables();
        nameGenerator.dropFunctions();
        nameGenerator.createAndPopulateTablesAndFunctions();

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
        first_name = male
                ? nameGenerator.getRandomMaleFirstName().replace("'", "")
                : nameGenerator.getRandomFemaleFirstName().replace("'", "");
        last_name = male
                ? nameGenerator.getRandomMaleLastName().replace("'", "")
                : nameGenerator.getRandomFemaleLastName().replace("'", "");

        password = generateRandomString(20);
        marketing_cons_id = random.nextInt(1, marketingConsentGenerator.getRecordCount());
        bank_branch_id = random.nextInt(1, bankBranchGenerator.getRecordCount());

        do {
            phone_number = "" + random.nextLong(500_000_000L, 900_000_000L);
            email = (first_name + "_" + last_name).replaceAll("[^\\w]+", "")
                    + random.nextInt(0, 100) + "@gmail.com";
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
