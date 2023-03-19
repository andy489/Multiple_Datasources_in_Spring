package com.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {
        "com.sources.configuration",
        "com.sources.controller",
        "com.sources.repository.bar",
        "com.sources.repository.bar",
        "com.sources.model.entity.foo",
        "com.sources.model.entity.bar"
}
)
public class MultipleDataSourcesApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleDataSourcesApplication.class);

    private static final String PSQL_DB_NAME = "bars";

    public static void main(String[] args) {
        createDatabaseIfNotExistsInPostgreSQL();
        SpringApplication.run(MultipleDataSourcesApplication.class, args);
    }

    private static void createDatabaseIfNotExistsInPostgreSQL() {

        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
                Statement statement = connection.createStatement();
        ) {
            LOGGER.debug(String.format("Creating database %s if not exist...", PSQL_DB_NAME));
            statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = '" + PSQL_DB_NAME + "'");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();

            int count = resultSet.getInt(1);

            if (count <= 0) {
                statement.executeUpdate("CREATE DATABASE " + PSQL_DB_NAME);
                LOGGER.debug(String.format("Database %s created.", PSQL_DB_NAME));
            } else {
                LOGGER.debug(String.format("Database %s already exist.", PSQL_DB_NAME));
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        }
    }

}
