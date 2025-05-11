package ma.ensa.db;

import ma.ensa.db.exceptions.DatabaseConnectionException;
import ma.ensa.db.exceptions.DatabaseQueryException;
import ma.ensa.util.CSVTestDataLoader;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    protected DatabaseManager dbManager;

    @BeforeEach
    void setUp() throws DatabaseConnectionException {
        dbManager = new DatabaseManagerImpl("test-db.properties");
        try {
            dbManager.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100))");
            dbManager.executeUpdate("DELETE FROM users");
        } catch (DatabaseQueryException e) {
            fail("Failed to setup test table", e);
        }
    }

    @AfterEach
    void tearDown() throws DatabaseConnectionException {
        dbManager.close();
    }

    @Test
    @DisplayName("Test INSERT and SELECT with CSV data")
    void testInsertAndSelectWithCSVData() throws DatabaseQueryException {
        List<Map<String, String>> testData = CSVTestDataLoader.loadTestData("test-data.csv");

        for (Map<String, String> row : testData) {
            // Insertion
            String insertSql = String.format(
                    "INSERT INTO users(id, name, email) VALUES(%s, '%s', '%s')",
                    row.get("id"), row.get("name"), row.get("email"));

            int rowsAffected = dbManager.executeUpdate(insertSql);
            assertEquals(1, rowsAffected, () -> "Should insert 1 row");

            // Vérification
            String selectSql = "SELECT * FROM users WHERE id = " + row.get("id");
            List<Map<String, Object>> results = dbManager.executeQuery(selectSql);

            assertFalse(results.isEmpty(), () -> "Should return data for id=" + row.get("id"));
            assertEquals(row.get("name"), results.get(0).get("name"));
            assertEquals(row.get("email"), results.get(0).get("email"));
        }
    }
}