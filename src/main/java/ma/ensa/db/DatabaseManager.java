package ma.ensa.db;

import ma.ensa.db.exceptions.DatabaseConnectionException;
import ma.ensa.db.exceptions.DatabaseQueryException;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DatabaseManager extends AutoCloseable {
    Connection getConnection() throws DatabaseConnectionException;
    List<Map<String, Object>> executeQuery(String sql) throws DatabaseQueryException;
    int executeUpdate(String sql) throws DatabaseQueryException;

    @Override
    void close() throws DatabaseConnectionException;
}
