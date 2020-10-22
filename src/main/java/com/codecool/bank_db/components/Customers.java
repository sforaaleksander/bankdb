package com.codecool.bank_db.components;

import java.util.HashSet;
import java.util.Set;

public class Customers {
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
