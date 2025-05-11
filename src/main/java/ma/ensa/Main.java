package ma.ensa;
import ma.ensa.db.DatabaseManager;
import ma.ensa.db.DatabaseManagerImpl;
import ma.ensa.db.exceptions.DatabaseConnectionException;
import ma.ensa.db.exceptions.DatabaseQueryException;

import java.util.List;
import java.util.Map;
public class Main {
    public static void main(String[] args) {
        // Utilisation de try-with-resources pour une gestion automatique des ressources
        try (DatabaseManager dbManager = new DatabaseManagerImpl("db.properties")) {

            // 1. Exemple de requête INSERT
            int rowsAffected = dbManager.executeUpdate(
                    "INSERT INTO users(name, email) VALUES('John Doe', 'john@example.com')");
            System.out.println("Lignes affectées par l'INSERT: " + rowsAffected);

            // 2. Exemple de requête SELECT
            List<Map<String, Object>> results = dbManager.executeQuery("SELECT * FROM users");
            System.out.println("\nRésultats de la requête SELECT:");
            results.forEach(row -> {
                System.out.println("ID: " + row.get("id") +
                        ", Nom: " + row.get("name") +
                        ", Email: " + row.get("email"));
            });

        } catch (DatabaseConnectionException e) {
            System.err.println("Erreur de connexion à la base de données:");
            e.printStackTrace();
        } catch (DatabaseQueryException e) {
            System.err.println("Erreur d'exécution de requête:");
            e.printStackTrace();
        }
    }
}