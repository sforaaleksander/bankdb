# bankdb
It this project we tried to mimic real world enterprise database with millions of rows, and then optimize it.
We chose database for bank, filled it with around 24 millions of randomly generated records, and optimized using indexes, and views. We also created triggers, and functions using PLpgSQL.

## Getting Started

### Prerequisites

* Maven 3.6
* Java 14

### Executing program
To create sql script with random data:
1. You can change final variables in collectGeneratedInsertsToFile method in https://github.com/sforaaleksander/bankdb/blob/master/src/main/java/com/codecool/bank_db/file_handlers/FileWriter.java to control how much data to generate.
2. Run App in Intellij, or
create jar with
`mvn install`
and run it
`java -jar /path/jar_name.jar`

file named db_populate.sql will be created in project root directory
