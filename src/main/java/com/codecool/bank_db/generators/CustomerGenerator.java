package com.codecool.bank_db.generators;

import com.codecool.bank_db.components.Customers;
import com.codecool.bank_db.file_handlers.RandomLineProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerGenerator extends UniqueDataGenerator {
    private final ThreadLocalRandom random;
    private final RandomLineProvider maleFirstNameRandomLineProvider;
    private final RandomLineProvider femaleFirstNameRandomLineProvider;
    private final RandomLineProvider maleLastNameRandomLineProvider;
    private final RandomLineProvider femaleLastNameRandomLineProvider;
    private final RandomLineProvider emailDomainsRandomLineProvider;
    private MarketingConsentGenerator marketingConsentGenerator;
    private BankBranchGenerator bankBranchGenerator;
    private final String[] polishLetters = new String[] {"ą", "ę", "ć", "ż", "ź", "ó", "ł", "ń", "ś"};
    private final String[] nonPolishLetters = new String[] {"a", "e", "c", "z", "z", "o", "l", "n", "s"};
    private final String[] EMAIL_SEPARATORS = {".", "_", "-", ""};



    public CustomerGenerator(Integer recordCount) {
        super(recordCount);
        random = ThreadLocalRandom.current();
        maleFirstNameRandomLineProvider = new RandomLineProvider("src/main/resources/male_first_names.txt");
        femaleFirstNameRandomLineProvider = new RandomLineProvider("src/main/resources/female_first_names.txt");
        maleLastNameRandomLineProvider = new RandomLineProvider("src/main/resources/male_last_names.txt");
        femaleLastNameRandomLineProvider = new RandomLineProvider("src/main/resources/female_last_names.txt");
        emailDomainsRandomLineProvider = new RandomLineProvider("src/main/resources/email_domains.txt");
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
                ? maleFirstNameRandomLineProvider.getRandomLine()
                : femaleFirstNameRandomLineProvider.getRandomLine();
        last_name = male
                ? maleLastNameRandomLineProvider.getRandomLine()
                : femaleLastNameRandomLineProvider.getRandomLine();
        password = generateRandomString(20);
        marketing_cons_id = random.nextInt(1, marketingConsentGenerator.getRecordCount());
        bank_branch_id = random.nextInt(1, bankBranchGenerator.getRecordCount());

        do {
            phone_number = "" + random.nextLong(500_000_000L, 900_000_000L);
            email = collectEmailElements(first_name, last_name);
            pesel = generatePesel(male);
            email = removePolishLetters(email);
        } while (customers.getPhoneNumbers().contains(phone_number) ||
                customers.getEmails().contains(email) ||
                customers.getPesels().contains(pesel));
        customers.addPhoneNumber(phone_number);
        customers.addEmail(email);
        customers.addPesel(pesel);

        return String.format("insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)\n" +
                "VALUES('%s', '%s', '%s', '%s', '%s', '%s', %s, %s);", first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id);
    }

    private String collectEmailElements(String first_name, String last_name) {
        return first_name.toLowerCase().substring(0, random.nextInt(2, first_name.length()))
                        + EMAIL_SEPARATORS[random.nextInt(EMAIL_SEPARATORS.length)]
                        + last_name.toLowerCase()
                        + random.nextInt(100)
                        + "@"
                        + emailDomainsRandomLineProvider.getRandomLine();
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

    private String removePolishLetters(String email) {
        for (int i=0;i<polishLetters.length;i++) {
            email = email.replace(polishLetters[i], nonPolishLetters[i]);
        }
        return email;
    }
}
