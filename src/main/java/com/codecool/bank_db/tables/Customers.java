package com.codecool.bank_db.tables;

import java.util.HashSet;
import java.util.Set;

public class Customers {
    static final String[] MALE_NAMES = {"Piotr", "Krzysztof", "Andrzej", "Tomasz", "Jan", "Paweł", "Michał", "Marcin", "Stanisław", "Jakub", "Adam", "Marek", "Łukasz", "Grzegorz", "Mateusz", "Wojciech", "Mariusz", "Dariusz", "Zbigniew", "Jerzy", "Maciej", "Rafał", "Robert", "Kamil", "Józef", "Jacek", "Dawid", "Tadeusz", "Ryszard", "Szymon"};
    static final String[] MALE_SURNAMES = {"Nowak", "Kowalski", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński", "Lewandowski", "Zieliński", "Woźniak", "Szymański", "Dąbrowski", "Kozłowski", "Mazur", "Jankowski", "Kwiatkowski", "Wojciechowski", "Krawczyk", "Kaczmarek", "Piotrowski", "Grabowski", "Zając", "Pawłowski", "Król", "Michalski", "Wróbel", "Wieczorek", "Jabłoński", "Nowakowski", "Majewski", "Olszewski", "Dudek", "Stępień", "Jaworski", "Adamczyk", "Malinowski", "Górski", "Pawlak", "Nowicki", "Sikora", "Witkowski", "Rutkowski", "Walczak", "Baran", "Michalak", "Szewczyk", "Ostrowski", "Tomaszewski", "Zalewski", "Wróblewski", "Pietrzak"};
    static final String[] FEMALE_NAMES = {"Anna", "Maria", "Katarzyna", "Małgorzata", "Agnieszka", "Barbara", "Ewa", "Krystyna", "Elżbieta", "Magdalena", "Joanna", "Zofia", "Aleksandra", "Monika", "Teresa", "Danuta", "Natalia", "Karolina", "Marta", "Julia", "Beata", "Dorota", "Janina", "Jadwiga", "Halina", "Jolanta", "Alicja", "Irena", "Grażyna", "Iwona"};
    static final String[] FEMALE_SURNAMES = {"Nowak", "Kowalska", "Wiśniewska", "Wójcik", "Kowalczyk", "Kamińska", "Lewandowska", "Zielińska", "Szymańska", "Dąbrowska", "Woźniak", "Kozłowska", "Jankowska", "Mazur", "Kwiatkowska", "Wojciechowska", "Krawczyk", "Kaczmarek", "Piotrowska", "Grabowska", "Pawłowska", "Michalska", "Zając", "Król", "Wieczorek", "Jabłońska", "Wróbel", "Nowakowska", "Majewska", "Olszewska", "Adamczyk", "Jaworska", "Malinowska", "Stępień", "Dudek", "Górska", "Nowicka", "Pawlak", "Witkowska", "Sikora", "Walczak", "Rutkowska", "Michalak", "Szewczyk", "Ostrowska", "Baran", "Tomaszewska", "Pietrzak", "Zalewska", "Wróblewska"};
    private final Set<String> phoneNumbers;
    private final Set<String> emails;
    private final Set<String> pesels;

    public Customers(Integer recordCount) {
        phoneNumbers = new HashSet<>(recordCount);
        emails = new HashSet<>(recordCount);
        pesels = new HashSet<>(recordCount);
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void addPhoneNumber(String phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public Set<String> getPesels() {
        return pesels;
    }

    public void addPesel(String pesel) {
        pesels.add(pesel);
    }
}
