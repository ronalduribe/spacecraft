package com.test.app.spacecraft.flyway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FlywayIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testFlywayMigration() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM SPACECRAFTS")) {

            assertTrue(resultSet.next());
            assertTrue(resultSet.getInt(1) > 0);
        }
    }
}
