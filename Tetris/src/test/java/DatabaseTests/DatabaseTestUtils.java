package DatabaseTests;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import tetris.dao.Database;


public class DatabaseTestUtils {
    private Map<String, Integer> scores;

    public DatabaseTestUtils() {
        initializeScores();
    }
    
    private void initializeScores() {
        scores = new HashMap();
        scores.put("Mikki", 1000);
        scores.put("Minni", 2000);
        scores.put("Pingu", 5000);
        scores.put("Roope", 10000);
        scores.put("Aku", 100);
    }
    
    public void emptyHighscores(Database database) throws SQLException {
        database.connect().prepareStatement("DROP TABLE highscores").execute();
    }
    
    public void initializeTestDb(Database database) throws SQLException {
        emptyHighscores(database);
        for (String name : scores.keySet()) {
            database.connect().prepareStatement(
                "INSERT INTO highscores (name, score) VALUES ( \"" + name + 
                        "\", " + scores.get(name) + ");")
                .execute();
        }
    }
}
