package ma.ensa.db;

import lombok.extern.log4j.Log4j2;
import ma.ensa.db.exceptions.DatabaseConnectionException;
import ma.ensa.db.exceptions.DatabaseQueryException;
import ma.ensa.util.DBConfigLoader;
import ma.ensa.util.QueryUtils;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class DatabaseManagerImpl implements DatabaseManager {
    private Connection connection;
    private final Properties config;

    public DatabaseManagerImpl(String configFile) throws DatabaseConnectionException {
        this.config = DBConfigLoader.loadConfig(configFile);
        connect();
    }

    private void connect() throws DatabaseConnectionException {
        try {
            String url = config.getProperty("db.url");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
            log.info(" Connected to database successfully");
        } catch (SQLException e) {
            log.error(" Database connection failed", e);
            throw new DatabaseConnectionException("Failed to connect to database", e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<Map<String, Object>> executeQuery(String sql) throws DatabaseQueryException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return QueryUtils.resultSetToList(rs);
        } catch (SQLException e) {
            log.error("Query execution failed: " + sql, e);
            throw new DatabaseQueryException("Failed to execute query: " + sql, e);
        }
    }

    @Override
    public int executeUpdate(String sql) throws DatabaseQueryException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            log.error(" Update execution failed: " + sql, e);
            throw new DatabaseQueryException("Failed to execute update: " + sql, e);
        }
    }

    @Override
    public void close() throws DatabaseConnectionException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                log.info(" Database connection closed");
            }
        } catch (SQLException e) {
            log.error(" Failed to close database connection", e);
            throw new DatabaseConnectionException("Failed to close database connection", e);
        }
    }
}