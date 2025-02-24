# Challenge Backedn

This project was generated using [Spring Initializr](https://start.spring.io/) using the latest version of Spring Boot, which I think it's 3.4.3.

## Setup

Follow the steps below so you can use this application as intended:

1. You will need to install some softwares first, some optional:
  - Java Development Kit (JDK) \[required\]:
    - [Versão 23](https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html);
    - [Versão 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html);
  - Maven:
    - [Versão 3.9.9](https://maven.apache.org/download.cgi);
  - IntelliJ IDEA:
    - [Community Edition](https://www.jetbrains.com/pt-br/idea/download);
  - PostgreSQL Database \[required\]:
    - [Versão 17](https://www.postgresql.org/download/)
2. Create a database, in your Postgres Instance, called `noxtec`;
3. Configure your environment, putting java in your Path Variable and also the Maven. (This step can be skipped if you are using IntelliJ IDEA)
4. Run the following command to build the project and also clean any early builds: (This step can be skipped if you are using IntelliJ IDEA)
```bash
mvn clean package
```
5. Run the project:
  - If you are using IntelliJ IDEA:
    - Open the `ChallengeApiApplication.java` file and click on the green play icon on the IDE;
  - If you are not using IntelliJ IDEA:
    - run the following command to run the application:
    ```bash
    mvn spring-boot:run
    ```
6. After running the project the connection to the database is going to be as defined in your `application.properties` file:
```properties
spring.application.name=challenge-api
spring.datasource.url=jdbc:postgresql://localhost:5432/noxtec
spring.datasource.username=<username>
spring.datasource.password=<senha>

spring.jpa.hibernate.ddl-auto=update
```

After running this project you can easily run the [frontend](https://github.com/gabrielteodosio/noxtec-challenge-frontend) application and run everything as intended.