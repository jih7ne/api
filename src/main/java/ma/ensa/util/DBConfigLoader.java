package ma.ensa.util;

import lombok.extern.log4j.Log4j2;
import ma.ensa.db.exceptions.DatabaseConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class DBConfigLoader {
    public static Properties loadConfig(String configFile) throws DatabaseConnectionException {
        Properties props = new Properties();
        try (InputStream input = DBConfigLoader.class.getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                throw new DatabaseConnectionException("Sorry, unable to find " + configFile);
            }
            props.load(input);
            log.info("Database configuration loaded successfully");
            return props;
        } catch (IOException e) {
            log.error("Error loading database configuration", e);
            throw new DatabaseConnectionException("Error loading database configuration", e);
        }
    }
}