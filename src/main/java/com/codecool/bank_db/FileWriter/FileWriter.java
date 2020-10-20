package com.codecool.bank_db.FileWriter;

import com.codecool.bank_db.tables.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class FileWriter {
    public void collectGeneratedInsertsToFile() throws IOException {
        int customersNo = 500_000;
        int atmsNo = 25_000;
        int bankBranchesNo = 25_000;
        int addressNo = 150_000;
        MarketingConsentsGenerator marketingConsentsGenerator = new MarketingConsentsGenerator();
        ProvinceGenerator provinceGenerator = new ProvinceGenerator();
        TransactionTypeGenerator transactionTypeGenerator = new TransactionTypeGenerator();
        AddressTypeGenerator addressTypeGenerator = new AddressTypeGenerator();

        AddressGenerator addressGenerator = new AddressGenerator(addressNo);
        BankBranchGenerator bankBranchGenerator = new BankBranchGenerator(bankBranchesNo);
        AtmGenerator atmGenerator = new AtmGenerator(atmsNo);
        CardGenerator cardGenerator = new CardGenerator(customersNo);
        AccountGenerator accountGenerator = new AccountGenerator(customersNo);

        DataGenerator[] generators = {marketingConsentsGenerator, provinceGenerator, transactionTypeGenerator,
                                        addressTypeGenerator, addressGenerator, bankBranchGenerator, atmGenerator,
                                        cardGenerator, accountGenerator};

        PrintWriter writer = new PrintWriter("sql_script.sql", StandardCharsets.UTF_8);
//        writer.println("The first line");
//        writer.println("The second line");


        for (DataGenerator generator : generators) {
            writer.print(generator.generate());
        }
        writer.close();
    }
}
