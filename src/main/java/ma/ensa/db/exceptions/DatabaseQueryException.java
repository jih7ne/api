package ma.ensa.db.exceptions;

public class DatabaseQueryException extends Exception {
    public DatabaseQueryException(String message) {
        super(message);
    }

    public DatabaseQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}