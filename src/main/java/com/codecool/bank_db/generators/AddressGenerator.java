package com.codecool.bank_db.generators;

import com.codecool.bank_db.file_handlers.RandomLineProvider;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class AddressGenerator extends UniqueDataGenerator {
    private final RandomLineProvider randomLineProvider;

    public AddressGenerator(Integer recordCount) throws IOException {
        super(recordCount);
        randomLineProvider = new RandomLineProvider("src/main/resources/streets.txt");
    }

    @Override
    public void generate(PrintWriter writer) {
        Set<String> inserts = new HashSet<>(recordCount);

        String insert;
        while (inserts.size() < recordCount) {
            try {
                insert = generateOne(inserts);
                inserts.add(insert);
                writer.println(insert + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateOne(Set<String> inserts) throws IOException {
        final Map<Integer, String[]> cities = new HashMap<>();
        cities.put(1, new String[]{"Wrocław", "Wałbrzych"});
        cities.put(2, new String[]{"Bydgoszcz", "Toruń"});
        cities.put(3, new String[]{"Lublin", "Zamość"});
        cities.put(4, new String[]{"Gorzów Wielkopolski", "Zielona Góra"});
        cities.put(5, new String[]{"Łódź", "Piotrków Trybunalski"});
        cities.put(6, new String[]{"Kraków", "Tarnów"});
        cities.put(7, new String[]{"Warszawa", "Radom"});
        cities.put(8, new String[]{"Opole", "Kędzierzyn-Koźle"});
        cities.put(9, new String[]{"Rzeszów", "Przemyśl"});
        cities.put(10, new String[]{"Białystok", "Suwałki"});
        cities.put(11, new String[]{"Gdańsk", "Gdynia"});
        cities.put(12, new String[]{"Katowice", "Częstochowa"});
        cities.put(13, new String[]{"Kielce", "Ostrowiec Świętokrzyski"});
        cities.put(14, new String[]{"Olsztyn", "Elbląg"});
        cities.put(15, new String[]{"Poznań", "Konin"});
        cities.put(16, new String[]{"Szczecin", "Koszalin"});

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int provinceId = random.nextInt(1, 17);

        String[] provinceCities = cities.get(provinceId);
        String city = provinceCities[random.nextInt(0, provinceCities.length)];
        String street = randomLineProvider.getRandomLine();
        String number = random.nextInt(0, 100) + "/" + random.nextInt(0, 200);
        String postcode = String.format("%05d", random.nextInt(1, 100_000));

        String insert = String.format("insert into addresses(street, number, city, postcode, province_id)\n" +
                "values ('%s', '%s', '%s', '%s', %s);", street, number, city, postcode, provinceId);
        return inserts.contains(insert) ? generateOne(inserts) : insert;
    }
}
