package com.codecool.bank_db.tables.name_generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NameGenerator {
    private final Connection connection;

    String[] FUNCTION_NAMES;
    String[] TABLE_NAMES;

    public NameGenerator() {
        connection = new PostgreSqlJDBC()
                .getConnection("jdbc:postgresql://localhost:5432/bankdb", "postgres", "postgres");

        setNames();
    }

    private void setNames() {
        String[] NAMES = {
                "male_first",
                "female_first",
                "male_last",
                "female_last"
        };
        FUNCTION_NAMES = new String[NAMES.length];
        TABLE_NAMES = new String[NAMES.length];

        for (int i = 0; i < NAMES.length; i++) {
            FUNCTION_NAMES[i] = "get_random_" + NAMES[i] + "_name";
            TABLE_NAMES[i] = NAMES[i] + "_names";
        }
    }

    public void createAndPopulateTablesAndFunctions() {
        populateNameDbsFromCsv();
        createFunctions();
    }

    public String getRandomMaleFirstName(int topRowsLimit) {
        return getRandomName(FUNCTION_NAMES[0], topRowsLimit);
    }

    public String getRandomFemaleFirstName(int topRowsLimit) {
        return getRandomName(FUNCTION_NAMES[1], topRowsLimit);
    }

    public String getRandomMaleLastName(int topRowsLimit) {
        return getRandomName(FUNCTION_NAMES[2], topRowsLimit);
    }

    public String getRandomFemaleLastName(int topRowsLimit) {
        return getRandomName(FUNCTION_NAMES[3], topRowsLimit);
    }

    public String getRandomName(String functionName, int count) {
        try (PreparedStatement stmt = connection.
                prepareStatement("SELECT " + functionName + "(" + count + ");");
             ResultSet results = stmt.executeQuery()
        ) {
            while (results.next()) {
                return results.getString(functionName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void populateNameDbsFromCsv() {
        boolean exists;
        for (int i = 0; i < TABLE_NAMES.length; i++) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT EXISTS(SELECT FROM information_schema.tables where table_name = '" + TABLE_NAMES[i] + "');"
            )
            ) {
                ResultSet results = stmt.executeQuery();
                results.next();
                exists = results.getBoolean("exists");

                if (!exists) {
                    createTable(i);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTable(int i) {
        String currentDir = System.getProperty("user.dir");
        String[] fileNames = {
                "/src/main/resources/imiona_męskie.csv",
                "/src/main/resources/imiona_żeńskie.csv",
                "/src/main/resources/nazwiska_męskie.csv",
                "/src/main/resources/nazwiska_żeńskie.csv"};

        try (PreparedStatement stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAMES[i] + " (id serial primary key, name text unique, count int);" +
                        "COPY " + TABLE_NAMES[i] + "(name, count) FROM '" + currentDir + fileNames[i] + "'  DELIMITER ',' CSV;"
        )
        ) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createFunctions() {
        for (int i = 0; i < FUNCTION_NAMES.length; i++) {
            createFunction(FUNCTION_NAMES[i], TABLE_NAMES[i]);
        }
    }

    private void createFunction(String functionName, String tableName) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "create or replace function " + functionName + "(number int) returns text as"
                        + " $$"
                        + " declare"
                        + "     count_sum bigint;"
                        + "     random_n  bigint;"
                        + "     x         record;"
                        + "     i         int := 0;"
                        + " begin"
                        + "     number := least(number, (SELECT count(*) FROM public." + tableName + ")::int);"
                        + "     count_sum := (select sum(c.count / last_count.count) as frequency"
                        + "                   from (select count from " + tableName + " limit number) c,"
                        + "                        (select * from " + tableName + " where id = number) last_count);"
                        + "     random_n := floor(random() * count_sum + 1);"

                        + "     for x in"
                        + "         select c.count / last_count.count as frequency"
                        + "         from (select count from " + tableName + " limit number) c,"
                        + "              (select * from " + tableName + " where id = number) last_count"
                        + "         loop"
                        + "             raise notice 'frequency: %', x.frequency;"
                        + "             i := i + 1;"
                        + "             random_n := random_n - x.frequency;"
                        + "             if random_n <= 0 then"
                        + "                 exit;"
                        + "             end if;"
                        + "         end loop;"
                        + "     return initcap((select name from " + tableName + " where id = i));"
                        + " end ;"
                        + " $$ language plpgsql;"
        )
        ) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropFunctions() {
        for (String functionName : FUNCTION_NAMES) {
            dropFunction(functionName);
        }
    }

    private void dropFunction(String functionName) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "drop function if exists " + functionName + "(number int);")
        ) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTables() {
        for (String tableName : TABLE_NAMES) {
            dropTable(tableName);
        }
    }

    private void dropTable(String tableName) {
        try (PreparedStatement stmt = connection.prepareStatement(
                " drop table if exists " + tableName + " cascade;")
        ) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
