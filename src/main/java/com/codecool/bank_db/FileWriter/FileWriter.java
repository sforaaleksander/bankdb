package com.codecool.bank_db.FileWriter;

import com.codecool.bank_db.tables.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class FileWriter {
    public void collectGeneratedInsertsToFile() throws IOException {
        int customersNo = 100;
        int atmsNo = 25;
        int bankBranchesNo = 25;
        int addressNo = 150;
        MarketingConsentGenerator marketingConsentGenerator = new MarketingConsentGenerator();
        ProvinceGenerator provinceGenerator = new ProvinceGenerator();
        TransactionTypeGenerator transactionTypeGenerator = new TransactionTypeGenerator();
        AddressTypeGenerator addressTypeGenerator = new AddressTypeGenerator();

        AddressGenerator addressGenerator = new AddressGenerator(addressNo);
        BankBranchGenerator bankBranchGenerator = new BankBranchGenerator(bankBranchesNo);
        AtmGenerator atmGenerator = new AtmGenerator(atmsNo);
        CardGenerator cardGenerator = new CardGenerator(customersNo);
        AccountGenerator accountGenerator = new AccountGenerator(customersNo);

        DataGenerator[] generators = {marketingConsentGenerator, provinceGenerator, transactionTypeGenerator,
                                        addressTypeGenerator, addressGenerator, bankBranchGenerator, atmGenerator,
                                        cardGenerator, accountGenerator};

        PrintWriter writer = new PrintWriter("sql_script.sql", StandardCharsets.UTF_8);

        for (DataGenerator generator : generators) {
            writer.print(generator.generate());
        }
        writer.close();
    }
}
