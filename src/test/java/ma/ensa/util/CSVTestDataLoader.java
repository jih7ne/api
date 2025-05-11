package ma.ensa.util;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ma.ensa.db.exceptions.DatabaseQueryException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CSVTestDataLoader {
    public static List<Map<String, String>> loadTestData(String csvFile) throws DatabaseQueryException {
        try (InputStream is = CSVTestDataLoader.class.getClassLoader().getResourceAsStream(csvFile);
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {

            List<String[]> allData = reader.readAll();
            if (allData.isEmpty()) throw new DatabaseQueryException("CSV file is empty");

            String[] headers = allData.get(0);
            List<Map<String, String>> result = new ArrayList<>();

            for (int i = 1; i < allData.size(); i++) {
                Map<String, String> row = new HashMap<>();
                for (int j = 0; j < headers.length; j++) {
                    row.put(headers[j], allData.get(i)[j]);
                }
                result.add(row);
            }
            return result;
        } catch (IOException | CsvException e) {
            throw new DatabaseQueryException("Failed to load test data from CSV", e);
        }
    }
}