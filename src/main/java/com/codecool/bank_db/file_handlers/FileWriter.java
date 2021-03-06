package com.codecool.bank_db.file_handlers;

import com.codecool.bank_db.generators.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

public class FileWriter {
    public void collectGeneratedInsertsToFile() throws IOException {
        int customersNo = 300_000;
        int atmsNo = 5000;
        int bankBranchesNo = 5000;
        int addressNo = 310_000;
        MarketingConsentGenerator marketingConsentGenerator = new MarketingConsentGenerator();
        ProvinceGenerator provinceGenerator = new ProvinceGenerator();
        TransactionTypeGenerator transactionTypeGenerator = new TransactionTypeGenerator();
        AddressTypeGenerator addressTypeGenerator = new AddressTypeGenerator();

        AccountLimitGenerator accountLimitGenerator = new AccountLimitGenerator(customersNo);
        BankBranchGenerator bankBranchGenerator = new BankBranchGenerator(bankBranchesNo);
        AtmGenerator atmGenerator = new AtmGenerator(atmsNo);
        CardGenerator cardGenerator = new CardGenerator(customersNo);
        AccountGenerator accountGenerator = new AccountGenerator(customersNo);
        CustomerGenerator customerGenerator = new CustomerGenerator(customersNo);
        CustomerAddressGenerator customerAddressGenerator = new CustomerAddressGenerator(customersNo);
        AddressGenerator addressGenerator = new AddressGenerator(addressNo);

        accountLimitGenerator.setCustomerGenerator(customerGenerator);
        bankBranchGenerator.setAddressGenerator(addressGenerator);
        atmGenerator.setAddressGenerator(addressGenerator);
        customerGenerator.setBankBranchGenerator(bankBranchGenerator);
        customerGenerator.setMarketingConsentGenerator(marketingConsentGenerator);
        accountGenerator.setCustomerGenerator(customerGenerator);
        cardGenerator.setAccountGenerator(accountGenerator);
        cardGenerator.setAtmGenerator(atmGenerator);
        customerAddressGenerator.setAddressGenerator(addressGenerator);
        customerAddressGenerator.setCustomerGenerator(customerGenerator);

        cardGenerator.setSetOfCardNumbers();

        DataGenerator[] generators = {
                marketingConsentGenerator,
                provinceGenerator,
                transactionTypeGenerator,
                addressTypeGenerator,
                addressGenerator,
                bankBranchGenerator,
                atmGenerator,
                customerGenerator,
                accountLimitGenerator,
                accountGenerator,
                cardGenerator,
                customerAddressGenerator
        };
        PrintWriter writer = new PrintWriter("db_populate.sql", StandardCharsets.UTF_8);

        for (DataGenerator generator : generators) {
            Instant start = Instant.now();

            generator.generate(writer);

            Instant end = Instant.now();
            System.out.println(generator.getClass().getSimpleName() + " took " + Duration.between(start, end));
        }
        writer.println("");
        writer.close();
    }
}
