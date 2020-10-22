package com.codecool.bank_db.tables;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerGenerator extends UniqueDataGenerator {
    private ThreadLocalRandom random;
    private MarketingConsentGenerator marketingConsentGenerator;
    private BankBranchGenerator bankBranchGenerator;

    public CustomerGenerator(Integer recordCount) {
        super(recordCount);
        random = ThreadLocalRandom.current();
    }

    public void setMarketingConsentGenerator(MarketingConsentGenerator marketingConsentGenerator) {
        this.marketingConsentGenerator = marketingConsentGenerator;
    }

    public void setBankBranchGenerator(BankBranchGenerator bankBranchGenerator) {
        this.bankBranchGenerator = bankBranchGenerator;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        final String[] EMAIL_SEPARATORS = {".", "_", "-", ""};
        Customers customers = new Customers(recordCount);
        for (int i = 0; i < recordCount; i++) {
            sb.append(generateOne(customers, EMAIL_SEPARATORS)).append("\n");
        }
        return sb.toString();
    }

    private String generateOne(Customers customers, String[] EMAIL_SEPARATORS) {
        String first_name, last_name, phone_number, email, password, pesel;
        int marketing_cons_id, bank_branch_id;
        boolean male = random.nextBoolean();
        first_name = male // TODO use db to generate names
                ? Customers.MALE_NAMES[random.nextInt(Customers.MALE_NAMES.length)]
                : Customers.FEMALE_NAMES[random.nextInt(Customers.FEMALE_NAMES.length)];
        last_name = male
                ? Customers.MALE_SURNAMES[random.nextInt(Customers.MALE_SURNAMES.length)]
                : Customers.FEMALE_SURNAMES[random.nextInt(Customers.FEMALE_SURNAMES.length)];
        password = generateRandomString(20);
        marketing_cons_id = random.nextInt(1, marketingConsentGenerator.getRecordCount());
        bank_branch_id = random.nextInt(1, bankBranchGenerator.getRecordCount());

        do {
            phone_number = "" + random.nextLong(500_000_000L, 900_000_000L);
            email = first_name.substring(1, random.nextInt(1, first_name.length()))
                    + EMAIL_SEPARATORS[random.nextInt(EMAIL_SEPARATORS.length)]
                    + last_name
                    + random.nextInt(100)
                    + "@" + Emails.EMAILS[random.nextInt(Emails.EMAILS.length)];
            pesel = generatePesel(male);
        } while (customers.getPhoneNumbers().contains(phone_number) ||
                customers.getEmails().contains(email) ||
                customers.getPesels().contains(pesel));

        customers.addPhoneNumber(phone_number);
        customers.addEmail(email.toLowerCase());
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
                + String.format("%03d", random.nextInt(1000))
                + (male ? random.nextInt(5) * 2 + 1 : random.nextInt(5) * 2);
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
