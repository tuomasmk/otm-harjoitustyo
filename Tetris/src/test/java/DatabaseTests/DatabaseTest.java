package DatabaseTests;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import tetris.components.Score;
import tetris.dao.Database;
import tetris.dao.ScoresDao;


public class DatabaseTest {
    
    private Database database;
    private ScoresDao dao;
    private DatabaseTestUtils utils;
    
//    private void emptyHighscores() throws SQLException {
//        database.connect().prepareStatement("DROP TABLE highscores").execute();
//    }
//    
//    private void initializeTestDb() throws SQLException {
//        emptyHighscores();
//        for (String name : scores.keySet()) {
//            database.connect().prepareStatement(
//                "INSERT INTO highscores (name, score) VALUES ( \"" + name + 
//                        "\", " + scores.get(name) + ");")
//                .execute();
//        }
//    }
    
    @Before
    public void setUp() throws SQLException {
        database = new Database("test.db");
        dao = new ScoresDao(database, "highscores");
        utils = new DatabaseTestUtils();
    }
    
    @Test
    public void findAllWithEmptyDatabase() throws SQLException {
        utils.emptyHighscores(database);
        List<Score> scores = dao.findAll();
        assertEquals(0, scores.size());
    }
    
    @Test
    public void findAllWithOneRowInDatabase() throws SQLException {
        utils.emptyHighscores(database);
        database.connect().prepareStatement(
                "INSERT INTO highscores (name, score) VALUES (\"Pingu\", 1000);")
                .execute();
        List<Score> result = dao.findAll();
        assertEquals(1, result.size());
        assertEquals("Pingu", result.get(0).getPlayer());
        assertEquals(1000, result.get(0).getScore());
    }
    
    @Test
    public void findAllWithFiveRowsInDatabase() throws SQLException {
        utils.initializeTestDb(database);
        List<Score> result = dao.findAll();
        assertEquals(5, result.size());
        Collections.sort(result);
        assertEquals("Roope", result.get(0).getPlayer());
        assertEquals(10000, result.get(0).getScore());
    }
    
    @Test
    public void addNewHighscore() throws SQLException {
        utils.initializeTestDb(database);
        dao.saveOrUpdate(new Score("Iines", 5500));
        List<Score> result = dao.findAll();
        assertEquals(6, result.size());
        Collections.sort(result);
        assertEquals("Iines", result.get(1).getPlayer());
        assertEquals(5500, result.get(1).getScore());
    }
}
