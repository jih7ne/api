# Database Manager API - TP POO Java

##  Description
API Java permettant une gestion unifiée des connexions aux SGBD relationnels (MySQL, PostgreSQL, SQL Server).  
Réalisée dans le cadre du TP de Programmation Orientée Objet.

##  Fonctionnalités
-  Connexion à MySQL, PostgreSQL et SQL Server
-  Exécution de requêtes SQL (CRUD complet)
-  Résultats sous forme de `List<Map<String, Object>>`
-  Configuration centralisée via fichier `.properties`
-  Exceptions personnalisées pour une meilleure gestion des erreurs
-  Fermeture automatique des ressources
-  Logging avec Log4j2
-  Tests unitaires avec JUnit 5

##  Installation

### Option 1 - Utilisation directe du JAR
1. Télécharger le fichier `db-api-1.0.0.jar`
2. Ajouter le JAR aux dépendances de votre projet

### Option 2 - Compilation depuis les sources
```bash
git clone  https://github.com/jih7ne/api.git
cd db-api
mvn clean package
```
##  Configuration
Créer un fichier db.properties dans src/main/resources :
````
# MySQL (décommenter pour utiliser)
db.url=jdbc:mysql://localhost:3306/ma_base?useSSL=false&serverTimezone=UTC
db.user=root
db.password=

# PostgreSQL
# db.url=jdbc:postgresql://localhost:5432/ma_base
# db.user=postgres
# db.password=

# SQL Server
# db.url=jdbc:sqlserver://localhost:1433;databaseName=ma_base
# db.user=sa
# db.password=
````
##  Utilisation de base
````
import ma.ensa.db.DatabaseManager;
import ma.ensa.db.DatabaseManagerImpl;
import ma.ensa.db.exceptions.*;

public class Exemple {
    public static void main(String[] args) {
        try (DatabaseManager db = new DatabaseManagerImpl("db.properties")) {
            
            // INSERT
            int lignes = db.executeUpdate(
                "INSERT INTO users(name, email) VALUES('Test', 'test@exemple.com')");
            
            // SELECT
            var resultats = db.executeQuery("SELECT * FROM users");
            
            resultats.forEach(row -> {
                System.out.printf("ID: %s, Nom: %s, Email: %s%n",
                    row.get("id"), row.get("name"), row.get("email"));
            });
            
        } catch (DatabaseConnectionException | DatabaseQueryException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
````
##  Méthodes disponibles
| Méthode                  | Description                                           |
|--------------------------|-------------------------------------------------------|
| executeQuery(String sql) | Exécute une requête SELECT et retourne les résultats |
| executeUpdate(String sql)| Exécute INSERT/UPDATE/DELETE                          |
| getConnection()          | Retourne la connexion JDBC active                    |
| close()                  | Ferme la connexion (automatique avec try)            |

##  Dépendances techniques
Java 11+

Drivers JDBC:

MySQL 8.0.28

PostgreSQL 42.3.6

SQL Server 9.4.1

Outils:

Lombok 1.18.24

Log4j 2.17.2

JUnit 5.8.2 (tests)

OpenCSV 5.7.1 (tests)
##  Auteur & Licence

- **Auteur** : [Jihane Chouhe]
- **Email** : [jih77ne@gmail.com]
- **Année universitaire** : 2024/2025
- **Licence** : MIT (voir `LICENSE`)



