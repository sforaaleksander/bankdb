package com.codecool.bank_db.tables.name_generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NameGenerator {
    private static final int FIRST_NAME_LIMIT = 5000;
    private static final int LAST_NAME_LIMIT = 50000;
    private final Connection connection;
    String[] NAMES;
    String[] FUNCTION_NAMES;
    String[] TABLE_NAMES;

    public NameGenerator() {
        connection = new PostgreSqlJDBC()
                .getConnection("jdbc:postgresql://localhost:5432/bankdb", "postgres", "postgres");

        setNames();
    }

    private void setNames() {
        NAMES = new String[]{
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

    public String getRandomMaleFirstName() {
        return getRandomName(FUNCTION_NAMES[0]);
    }

    public String getRandomFemaleFirstName() {
        return getRandomName(FUNCTION_NAMES[1]);
    }

    public String getRandomMaleLastName() {
        return getRandomName(FUNCTION_NAMES[2]);
    }

    public String getRandomFemaleLastName() {
        return getRandomName(FUNCTION_NAMES[3]);
    }

    public String getRandomName(String functionName) {
        int count = functionName.equals("get_random_male_first_name") || functionName.equals("get_random_female_first_name")
                ? FIRST_NAME_LIMIT : LAST_NAME_LIMIT;
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
        createFunction(true, true);
        createFunction(false, true);
        createFunction(true, false);
        createFunction(false, false);
    }

    private void createFunction(boolean male, boolean firstName) {
        String name = male ? "male" : "female";
        name += firstName ? "_first" : "_last";
        try (PreparedStatement stmt = connection.prepareStatement(
                " create materialized view " + name + "_frequency_sum_view as\n" +
                        " select sum(c.count / last_count.count) as frequency\n" +
                        " from (select count from " + name + "_names limit " + (firstName ? FIRST_NAME_LIMIT : LAST_NAME_LIMIT) + ") c,\n" +
                        "      (select * from " + name + "_names where id = " + (firstName ? FIRST_NAME_LIMIT : LAST_NAME_LIMIT) + ") last_count;\n" +
                        " create materialized view " + name + "_frequency_view as\n" +
                        " select c.count / last_count.count as frequency\n" +
                        " from (select count from " + name + "_names limit " + (firstName ? FIRST_NAME_LIMIT : LAST_NAME_LIMIT) + ") c,\n" +
                        "      (select * from " + name + "_names where id = " + (firstName ? FIRST_NAME_LIMIT : LAST_NAME_LIMIT) + ") last_count;\n" +
                        "create or replace function get_random_" + name + "_name(number int) returns text as\n" +
                        "$$\n" +
                        "declare\n" +
                        "    count_sum bigint;\n" +
                        "    random_n  bigint;\n" +
                        "    x         record;\n" +
                        "    i         int := 0;\n" +
                        "begin\n" +
                        "    count_sum := (select frequency from " + name + "_frequency_sum_view);\n" +
                        "    random_n := floor(random() * count_sum + 1);\n" +
                        "\n" +
                        "    for x in\n" +
                        "        select frequency from " + name + "_frequency_view\n" +
                        "        loop\n" +
                        "            i := i + 1;\n" +
                        "            random_n := random_n - x.frequency;\n" +
                        "            if random_n <= 0 then\n" +
                        "                exit;\n" +
                        "            end if;\n" +
                        "        end loop;\n" +
                        "    return initcap((select name from " + name + "_names where id = i));\n" +
                        "end ;\n" +
                        "$$ language plpgsql;"
        )
        ) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropFunctions() {
        for (String name : NAMES) {
            dropFunction(name);
        }
    }

    private void dropFunction(String name) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "drop function if exists " + "get_random_" + name + "_name" + "(number int);" +
                        "drop materialized view " + name + "_frequency_sum_view;" +
                        "drop materialized view " + name + "_frequency_view;")
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
